package com.example.users.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "secret";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private final String ISSUER = "issuer";

    public String generateToken(UserDetails userDetails) {
        long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(ISSUER)
                .withClaim("roles", userDetails.getAuthorities().toString())
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
