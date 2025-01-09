package uz.behzod.restaurantApp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
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

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Long userId = null;
        Long companyId = null;
        Long branchId = null;
        Long departmentId = null;
        String language = null;
        String tin = null;

        if (authentication.getPrincipal() instanceof CustomUser customUser) {
            userId = customUser.getUserId();
            companyId = customUser.getCompanyId();
            branchId = customUser.getBranchId();
            departmentId = customUser.getDepartmentId();
            tin = customUser.getTin();
        }

        return createToken(authentication.getName(), authorities, rememberMe, userId, companyId, branchId, departmentId, language, tin);
    }

    private String createToken(String username, String role, boolean rememberMe, Long userId, Long companyId, Long branchId, Long departmentId, String language, String tin) {
        return Jwts.builder()
                .setSubject(username) //subject()
                .claim(AUTHORITIES_KEY, role)
                .claim(USER_ID, userId)
                .claim(COMPANY_ID, companyId)
                .claim(BRANCH_ID, branchId)
                .claim(DEPARTMENT_ID, departmentId)
                .claim(LANGUAGE, language)
                .claim(TIN, tin)
                .setExpiration(getNextExpiration(rememberMe)) //expiration()
                .signWith(key)
                .compact();
    }


    private Date getNextExpiration(boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + tokenValidityInSecondsForRememberMe * 1000);
        } else {
            validity = new Date(now + tokenValidityInSeconds * 1000);
        }
        return validity;
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

}
