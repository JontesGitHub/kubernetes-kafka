package microservice.booking_service.shared.auth;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/***
 * A utils class for handling JWT token
 */
@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /***
     * Gets the userId from a token
     * @param token token to parse
     * @return the userId from the token
     */
    public String getUserIdFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /***
     * Validates a token and throws an exception if it fails
     * @param token token to vaildate
     * @return true if successful
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "JWT token is expired: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "JWT claims string is empty: " + e.getMessage());
        }
    }
}
