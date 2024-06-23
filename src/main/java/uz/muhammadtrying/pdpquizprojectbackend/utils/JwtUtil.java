package uz.muhammadtrying.pdpquizprojectbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import uz.muhammadtrying.pdpquizprojectbackend.dto.LogInDTO;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    public String generateAccessToken(LogInDTO logInDTO) {
        // generating an access token which expires 10 seconds after being created
        return Jwts.builder()
                .subject(logInDTO.getEmail())
                .issuer("Muhammad's Production")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 10))
                .signWith(secretKey())
                .compact();
    }

    public String generateRefreshToken(LogInDTO logInDTO) {
        // generating a refresh token which is renewed on a daily basis
        return Jwts.builder()
                .subject(logInDTO.getEmail())
                .issuer("Muhammad's Production")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey())
                .compact();
    }

    private SecretKey secretKey() {
        byte[] bytes = Decoders.BASE64.decode("9876543219876543219876543219876543219876543219876543219876543210");
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean isValid(String token) {
        try {
            Claims claims = getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUserName(String token) {
        return getClaims(token).getSubject();
    }
}
