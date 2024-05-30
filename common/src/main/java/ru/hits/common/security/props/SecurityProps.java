package ru.hits.common.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app.security")
@Getter
@Setter
@ToString
@Component
public class SecurityProps {
    private SecurityJwtTokenProps jwtToken;
    private SecurityIntegrationsProps integrations;
}
