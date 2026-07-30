package com.cvn.user.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cvn.user.entity.User;
import com.cvn.user.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secret.key}")
    private String jwtSecret;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    /*
     * Generate Tokens
     */

    public String generateAccessToken(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());

        return generateToken(claims,
                user.getEmail(),
                accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {

        return generateToken(
                new HashMap<>(),
                user.getEmail(),
                refreshTokenExpiration);
    }

    private String generateToken(
            Map<String, Object> claims,
            String subject,
            long expiration) {

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /*
     * Extract Claims
     */

//    public String extractEmail(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token)
                .get("userId", Long.class);
    }

    public Role extractRole(String token) {   // changed return type
        return Role.valueOf(
            extractAllClaims(token).get("role", String.class)
        );
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /*
     * Validation
     */

    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String email = extractUsername(token);

        return email.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    /*
     * Private Helper Methods
     */

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {  //changed return type from Key to SecretKey

        byte[] keyBytes;

        try {
            keyBytes = Decoders.BASE64.decode(jwtSecret);
        } catch (Exception e) {
            keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
