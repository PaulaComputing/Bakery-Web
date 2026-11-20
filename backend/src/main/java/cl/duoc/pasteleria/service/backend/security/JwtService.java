package cl.duoc.pasteleria.service.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String correo, String rol) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", rol);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(correo)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public String extraerCorreo(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    public boolean esTokenValido(String token, UserDetails userDetails) {
        final String correo = extraerCorreo(token);
        Date expiracion = extraerClaim(token, Claims::getExpiration);
        return (correo.equalsIgnoreCase(userDetails.getUsername()) && !expiracion.before(new Date()));
    }
}
