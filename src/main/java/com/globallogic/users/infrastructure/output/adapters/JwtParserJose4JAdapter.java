package com.globallogic.users.infrastructure.output.adapters;

import com.globallogic.users.domain.exceptions.TokenException;
import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.output.TokenCreatorOutputPort;
import com.globallogic.users.infrastructure.config.properties.TokenProperties;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtParserJose4JAdapter implements TokenCreatorOutputPort {

    private final TokenProperties tokenProperties;

    private final RsaJsonWebKey rsaJsonWebKey;

    public JwtParserJose4JAdapter(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
        try {
            rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            rsaJsonWebKey.setKeyId(tokenProperties.getSecret());
        } catch (JoseException ex) {
            log.error("An exception occurred:", ex);
            throw new TokenException();
        }
    }

    @Override
    public String createToken(User user) {

        JwtClaims claims = new JwtClaims();
        claims.setIssuer(tokenProperties.getIssuer());
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture(tokenProperties.getMinutesLifespan());
        claims.setSubject(user.getId().toString());
        claims.setStringClaim("email", user.getEmail());

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        try {
            return jws.getCompactSerialization();
        } catch (JoseException ex) {
            log.error("An exception occurred:", ex);
            throw new TokenException();
        }
    }

    public JwtClaims parseJwtClaims(String token)  {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setSkipSignatureVerification()
                .setAllowedClockSkewInSeconds(60)
                .setRequireSubject()
                .setExpectedIssuer(tokenProperties.getIssuer())
                .setVerificationKey(rsaJsonWebKey.getKey())
                .setJwsAlgorithmConstraints(
                        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                                AlgorithmIdentifiers.RSA_USING_SHA256))
                .build();
        try {
            return jwtConsumer.processToClaims(token);
        } catch (InvalidJwtException ex) {
            log.error("An exception occurred:", ex);
            throw new TokenException();
        }
    }

    public boolean validateClaims(JwtClaims claims) {
        try {
            return claims.getExpirationTime().isAfter(NumericDate.now());
        } catch (MalformedClaimException ex) {
            log.error("An exception occurred:", ex);
            throw new TokenException();
        }
    }
}
