//package com.gr.learningpath.security.jwt;
//
//
//import com.gr.learningpath.api.request.user.LearningPathUser;
//import com.gr.learningpath.config.properties.ApplicationProperties;
//import com.gr.learningpath.config.properties.SecurityProperties;
//import com.gr.learningpath.exceptions.EntityNotFoundException;
//import com.gr.learningpath.repositories.UserRepository;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Nonnull;
//import javax.annotation.PostConstruct;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///// This class is the Heart of our token Generating System///////////////////////////
//@Component
//@Log4j2
//@RequiredArgsConstructor
//public class JwtTokenProvider {
//
//    private static final String AUTHORITIES_KEY = "auth";
//    @Nonnull
//    private final SecurityProperties securityProperties;
//    @Nonnull
//    private final ApplicationProperties applicationProperties;
//    @Nonnull
//    private final UserRepository userRepository;
//    private Key key;
//    private long tokenValidityInMilliseconds;
//    private long tokenValidityInMillisecondsForRememberMe;
//
//
//    @PostConstruct
//    private void setProperties() {
//        String secret = securityProperties.getJwt().getSecret();
//        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//        this.tokenValidityInMilliseconds = 1000 * securityProperties.getJwt().getTokenValidityInSeconds();
//        this.tokenValidityInMillisecondsForRememberMe = 1000 * securityProperties.getJwt().getTokenValidityInSecondsForRememberMe();
//
//    }
//
//
//    public String createToken(Authentication authentication, boolean rememberMe) {
//        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        long now = (new Date()).getTime();
//        Date validity;
//        if (rememberMe) {
//            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
//        } else {
//            validity = new Date(now + this.tokenValidityInMilliseconds);
//        }
//
//        // Creating of JWT token SignatureAlgorithm.HS512 algorithm is used
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .signWith(SignatureAlgorithm.HS512, key)
//                .setExpiration(validity)
//                .compact();
//    }
//
//
//    public Authentication getAuthentication(@Nonnull final String token) {
//        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
//        final com.gr.learningpath.domain.User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new EntityNotFoundException(com.gr.learningpath.domain.User.class));
//
//
//        final Set<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet());
//        final LearningPathUser principal = new LearningPathUser(user);
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }
//
//
//    // After retreaving token vai JWTFilter this validate token validates token and it's validity
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            log.info("Invalid JWT token.");
//            log.trace("Invalid JWT token trace.", e);
//        }
//        return false;
//    }
//
//}
