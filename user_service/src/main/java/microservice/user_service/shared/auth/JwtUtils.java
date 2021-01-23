package microservice.user_service.shared.auth;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/***
 * A utils class for handling JWT token
 */
@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMS}")
    private int jwtExpirationMs;

    /***
     * Creates a JWT token made by a userId, adds a JWT secret and JWT expiration time
     * @param userId userId to add to the token
     * @return a JWT token
     */
    public String generateJwtToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
