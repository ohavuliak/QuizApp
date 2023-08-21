package com.example.quizapp.securityTests;

import com.example.quizapp.security.service.JwtService;
import com.example.quizapp.security.service.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {
    private final String jwtSigningKey = "UoUadBpef4zpK5WhPVnitMIhqYbQNasAzPcTX5hMU85m/MRivALw4quXIV7JRDQh";
    private JwtService jwtService;
    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(userDetails.getUsername()).thenReturn("testUser");
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        jwtService = mock(JwtServiceImpl.class);
        when(jwtService.generateToken(userDetails)).thenCallRealMethod();
        when(jwtService.getSigningKey()).thenReturn(Keys.hmacShaKeyFor(keyBytes));
    }

    @Test
    public void testGenerateToken() {
        String generatedToken = jwtService.generateToken(userDetails);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSigningKey())
                .build()
                .parseClaimsJws(generatedToken)
                .getBody();
        assertEquals("testUser", claims.getSubject());
        System.out.println(generatedToken);
    }
}