package com.globallogic.users.infrastructure.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
@Data
public class TokenProperties {
    private String issuer;
    private String secret;
    private int minutesLifespan;
}
