package ru.hits.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class JwtUserData {
    private final UUID id;

    private final String login;

    private final String name;

}
