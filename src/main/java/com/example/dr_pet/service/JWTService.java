package com.example.dr_pet.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Transactional
public class JWTService {

    private String screateKey = "";


    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            screateKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while generating key");
        }

    }


    public String generateToken(String username) {

        //generate token
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60  * 60 * 10))
                .and()
                .signWith(getKey())
                .compact();


    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(screateKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetail) {
        final String username = extractUserName(token);
        return (username.equals(userDetail.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
