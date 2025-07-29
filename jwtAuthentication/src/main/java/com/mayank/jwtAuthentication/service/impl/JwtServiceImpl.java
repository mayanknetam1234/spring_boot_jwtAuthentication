package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final String secretKey="fjlkasdflkasfklsadnlkdsndnddkvdkvsalkmdsfsdkfsa";

    private final Long jwtExpiration=3600000L;
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    private String generateToken(Map<String,Object> claims,UserDetails userDetails){
        return buildToken(claims,userDetails,jwtExpiration);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, Long jwtExpiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSecretKey())
                .compact();
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        String email= extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    public String extractEmail(String token) {
        return extractClaim(token,(Claims::getSubject));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
