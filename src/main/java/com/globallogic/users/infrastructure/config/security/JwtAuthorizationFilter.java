package com.globallogic.users.infrastructure.config.security;

import com.globallogic.users.infrastructure.output.adapters.JwtParserJose4JAdapter;
import lombok.AllArgsConstructor;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private final JwtParserJose4JAdapter jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = null;

        String bearerToken = request.getHeader(TOKEN_HEADER);

        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            accessToken = bearerToken.substring(TOKEN_PREFIX.length());
        }

        if (accessToken == null ) {
            filterChain.doFilter(request, response);
            return;
        }

        JwtClaims claims = jwtParser.parseJwtClaims(accessToken);

        if(claims != null && jwtParser.validateClaims(claims)){

            Authentication authentication;

            try {
                authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", new ArrayList<>());
            } catch (MalformedClaimException e) {
                throw new RuntimeException(e);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
