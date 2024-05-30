package ru.hits.common.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.hits.common.security.props.SecurityProps;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProps.class)
public class SecurityConfig {
    private final SecurityProps securityProps;

    /**
     * Шифровальщик паролей
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настройка для JWT
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChainJwt(HttpSecurity http) {
        http = http.cors()
                .and()
                .securityMatcher(createCustomReqMatcher(
                        securityProps.getJwtToken().getRootPath(),
                        securityProps.getJwtToken().getPermitAll()))
                .addFilterBefore(
                        new JwtTokenFilter(securityProps.getJwtToken().getSecret()),
                        UsernamePasswordAuthenticationFilter.class
                )

                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        return finalize(http);
    }

    /**
     * Настройка для интеграции между сервисами
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChainIntegration(HttpSecurity http) {
        http = http.securityMatcher(filterPredicate(securityProps.getIntegrations().getRootPath()))
                .addFilterBefore(
                        new IntegrationFilter(securityProps.getIntegrations().getApiKey()),
                        UsernamePasswordAuthenticationFilter.class
                );
        return finalize(http);
    }

    @SneakyThrows
    private SecurityFilterChain finalize(HttpSecurity http) {
        return http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    private RequestMatcher filterPredicate(String rootPath, String... ignore) {
        // Условие, что
        // 1. Путь сервлета задан
        // 2. Путь сервлета соответствует требуемому паттерну (начинается с rootPath)
        // 3. Путь сервлета не соответствует паттерну игнорируемых путей
        return rq -> Objects.nonNull(rq.getServletPath())
                && rq.getServletPath().startsWith(rootPath)
                && Arrays.stream(ignore).noneMatch(item ->rq.getServletPath().startsWith(item));
    }
    private RequestMatcher createCustomReqMatcher(String rootPath, String... ignorePath) {
        return req -> {
            boolean result = Objects.nonNull(req.getServletPath())
                    && req.getServletPath().startsWith(rootPath)
                    && Arrays.stream(ignorePath).noneMatch(item -> req.getServletPath().startsWith(item));
            return result;
        };
    }
}
