package ru.hits.userApp.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.security.props.SecurityProps;
import ru.hits.userApp.entity.EmployeeEntity;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final SecurityProps securityProps;

    public String generateAccess(EmployeeEntity user){
        var key = Keys.hmacShaKeyFor(securityProps.getJwtToken().getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(user.getFullName())
                .setClaims(Map.of(
                        "login", user.getEmail(),
                        "id", user.getId(),
                        "name", user.getFullName()
                ))
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(currentTimeMillis() + 172800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
