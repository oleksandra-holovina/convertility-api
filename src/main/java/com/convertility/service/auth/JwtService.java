package com.convertility.service.auth;

import com.convertility.data.JwtDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtService.class.getName());
    private static final int BEARER_TOKEN_VALIDITY = 900;
    private static final int REFRESH_TOKEN_VALIDITY = 31536000;
    private static final Function<Integer, Date> validityToDate = (validity) -> new Date(System.currentTimeMillis() + validity * 1000L);

    @Value("${jwt.secret}")
    private String secret;

    public JwtDetails generateToken(String userId) {
        Date bearerExpiration = validityToDate.apply(BEARER_TOKEN_VALIDITY);
        Date refreshExpiration = validityToDate.apply(REFRESH_TOKEN_VALIDITY);

        return JwtDetails.builder()
                .bearerToken(generateToken(userId, bearerExpiration))
                .bearerTokenExpiration(BEARER_TOKEN_VALIDITY)
                .refreshToken(generateToken(userId, refreshExpiration))
                .refreshTokenExpiration(REFRESH_TOKEN_VALIDITY)
                .build();
    }

    private String generateToken(String userId, Date expiresAt) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Optional<String> extractToken(String authorization) {
        String[] split = authorization.split("\\s");
        if (split.length == 2 && "Bearer".equals(split[0])) {
            return Optional.ofNullable(split[1]).map(String::trim);
        }
        return Optional.empty();
    }

    public String getIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException exception) {
            return exception.getClaims().getSubject();
        }
    }
}
