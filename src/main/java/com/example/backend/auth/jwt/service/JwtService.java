package com.example.backend.auth.jwt.service;

import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String SECRET_KEY = "724aaebf7f5ac4f24567005c12bc9b6e3f4e4a2f1ed7a075f43ef2b235e8c4e3";
    private final UserRepository userRepository;

    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user) {
        return generateToken(user.getUsername());
    }

    public String generateToken(OAuth2User oAuth2User) {
        return generateToken(oAuth2User.getName());
    }

    private String generateToken(String subject) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 24 * 60 * 60 * 1000); // 24 hours validity

        SecretKey key = getSigninKey();

        return Jwts.builder()
                .claim("sub", subject)
                .claim("iat", now)
                .claim("exp", validity)
                .signWith(key)
                .compact();
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public UUID getUserIdFromToken(String token) {
        String username = extractUsername(token);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}
