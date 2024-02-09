package com.example.labxpert.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static com.example.labxpert.config.security.JwtTokenUtil.*;


@Component
public class JWTHelper {

    // we use this algorithm to crypt data and decrypt the JWT token
    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    // the access token is given to the user for a limited period of time
    public String generateAccessToken(String email, String roles) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRE))
                .withIssuer(ISSUER)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public String generateRefreshToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_EXPIRE))
                .withIssuer(ISSUER)
                .sign(algorithm);
    }

    public String extractTokenFromHeaderIfExists(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public Map<String, String> getTokensMap(String JWTAccessToken, String JWTRefreshToken){
        Map<String, String> idTokens = new HashMap<>();
        idTokens.put("AccessToken", JWTAccessToken);
        idTokens.put("RefreshToken", JWTRefreshToken);
        return idTokens;
    }

}
