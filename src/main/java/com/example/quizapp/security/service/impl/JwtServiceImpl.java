package com.example.quizapp.security.service.impl;

import com.example.quizapp.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    private boolean shouldCheckTokenValidity = false;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        long validityOfToken = 1000*60*24;
        Date expirationDate = new Date(System.currentTimeMillis() + validityOfToken);
        String token = Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

        log.info("Token generated for user '{}' is valid for {} milliseconds", userDetails.getUsername(), validityOfToken);

        long expirationTimeMillis = expirationDate.getTime();
        long currentTimeMillis = System.currentTimeMillis();
        if (expirationTimeMillis <= currentTimeMillis) {
            shouldCheckTokenValidity = true;
        }
        return token;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }


    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Scheduled(fixedDelay = 24*60*1000)
    public void checkTokenValidity(){
        log.warn("Token for the currently logged-in user is no longer valid");
        log.info("Token validity check completed.");
        shouldCheckTokenValidity = false;
    }

    @Scheduled(fixedDelay = 60*1000)
    public void conditionalTokenValidityCheck() {
        if (shouldCheckTokenValidity) {
            checkTokenValidity();
        }
    }
}