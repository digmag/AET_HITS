package ru.hits.common.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static ru.hits.common.security.SecurityConst.HEADER_JWT;
import static ru.hits.common.security.SecurityConst.HEADER_PREFIX;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final String secretKey;

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var jwt = request.getHeader(HEADER_JWT);
        if (jwt == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // парсинг токена
        JwtUserData userData;
        try {
            var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            var data = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt.replace(HEADER_PREFIX, ""));
            var idStr = String.valueOf(data.getBody().get("id"));
            userData = new JwtUserData(
                    idStr == null ? null : UUID.fromString(idStr),
                    String.valueOf(data.getBody().get("login")),
                    String.valueOf(data.getBody().get("name"))
            );
        } catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        var authentication = new JwtAuthentication(userData);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
