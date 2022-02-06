package com.professional.subscribee.jwt;

import com.professional.subscribee.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Base64;

import static java.util.Date.from;

@Component
public class JwtTokenProvider {
    private final JwtUserDetailsService userDetailsService;
    private final String secret;
    private final long jwtExpirationInMillis;

    public JwtTokenProvider(@Lazy JwtUserDetailsService userDetailsService,
                            @Value("${jwt.token.secret}") String secret,
                            @Value("${jwt.token.expiration.time}") long jwtExpirationInMillis) {
        this.userDetailsService = userDetailsService;
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.jwtExpirationInMillis = jwtExpirationInMillis;
    }

    public String createToken(String phone, Role role) {
        Claims claims = Jwts.claims().setSubject(phone);
        claims.put("role", role.getRoleName());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .setIssuedAt(from(Instant.now()))
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByPhone(getPhoneFromJwt(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getPhoneFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parse(token);
        return true;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
