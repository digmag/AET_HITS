package ru.hits.common.security;


import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConst {
    public static final String HEADER_JWT = "Authorization";

    public static final String HEADER_PREFIX = "Bearer ";
    public static final String HEADER_API_KEY = "API_KEY";
}
