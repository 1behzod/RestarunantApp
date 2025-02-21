package uz.behzod.restaurantApp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.constants.AuthConstants;
import uz.behzod.restaurantApp.security.CustomUser;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenProvider implements InitializingBean, AuthConstants {

    SecretKey key;

    @Value("${security.authentication.jwt.secret-key}")
    String secretKey;

    @Value("${security.authentication.jwt.token-validity-in-seconds}")
    Long tokenValidityInSeconds;

    @Value("${security.authentication.jwt.token-validity-in-seconds-for-remember-me}")
    Long tokenValidityInSecondsForRememberMe;

    @Override
    public void afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public Authentication getAuthentication(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // verifyWith()
                .build()
                .parseClaimsJws(token)
                .getBody();

        String grantedAuthorities = claims.get(AUTHORITIES_KEY).toString();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(grantedAuthorities.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        Long userId = null;
        if (claims.containsKey(USER_ID)) {
            userId = Long.valueOf((Integer) claims.get(USER_ID));
        }
        Long companyId = null;
        if (claims.containsKey(COMPANY_ID)) {
            companyId = Long.valueOf((Integer) claims.get(COMPANY_ID));
        }
        Long branchId = null;
        if (claims.containsKey(BRANCH_ID)) {
            branchId = Long.valueOf((Integer) claims.get(BRANCH_ID));
        }
        Long departmentId = null;
        if (claims.containsKey(DEPARTMENT_ID)) {
            departmentId = Long.valueOf((Integer) claims.get(DEPARTMENT_ID));
        }
        String language = null;
        if (claims.containsKey(LANGUAGE)) {
            language = (String) claims.get(LANGUAGE);
        }
        String tin = null;
        if (claims.containsKey(TIN)) {
            tin = (String) claims.get(TIN);
        }

        CustomUser customUser = new CustomUser(
                claims.getSubject(),
                "",
                authorities,
                userId,
                companyId,
                branchId,
                departmentId,
                tin
        );
        return new UsernamePasswordAuthenticationToken(customUser, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key) // verifyWith()
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }


    public Map<String, String> createToken(String username, boolean rememberMe) {
        long now = System.currentTimeMillis();
        Date accessTokenExpiry = getNextExpiration(rememberMe);

        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(accessTokenExpiry)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }


    public Date getNextExpiration(boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + tokenValidityInSecondsForRememberMe * 1000);
        } else {
            validity = new Date(now + tokenValidityInSeconds * 1000);
        }
        return validity;
    }
}
