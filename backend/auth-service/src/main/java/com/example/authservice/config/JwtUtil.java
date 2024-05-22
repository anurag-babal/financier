package com.example.authservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authservice.domain.model.Login;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "secret";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private final String ISSUER = "issuer";

    public String generateToken(Login login) {
        long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
        return JWT.create()
                .withSubject(login.getUsername())
                .withClaim("userId", login.getUserId())
                .withIssuer(ISSUER)
                .withClaim("roles", login.getRoles().toString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        jwtVerifier.verify(token);
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return userDetails.getUsername().equals(decodedJWT.getSubject());
    }
}
