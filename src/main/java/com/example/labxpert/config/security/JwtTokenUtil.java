package com.example.labxpert.config.security;


import org.springframework.stereotype.Component;


@Component
public class JwtTokenUtil {

    public static final long JWT_ACCESS_TOKEN_EXPIRE = 10 * 60 * 1000;

    public static final long JWT_REFRESH_TOKEN_EXPIRE = 120 * 60 * 1000;

    public static final String ISSUER = "YassinBoot Security Application";

    public static final String SECRET ="myPrivateSecret";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String AUTH_HEADER = "Authorization";

}
