package ir.stream.app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ir.stream.app.dto.AccessTokenDTO;
import ir.stream.app.dto.AuthenticationTokenDTO;
import ir.stream.app.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final String jwtSigningKey = "secre99T";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public AuthenticationTokenDTO generateToken(User user) {
        AccessTokenDTO accessTokenDTO = createToken(new HashMap<>(), user);

        return new AuthenticationTokenDTO(
                accessTokenDTO.getToken(),
                accessTokenDTO.getExpireAt().getTime(),
                user.getUsername()
        );
    }

    private AccessTokenDTO createToken(Map<String, Object> claims, User user) {
        // Todo: change expiration time
        Date expirationDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getRoles())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey)
                .compact();

        return new AccessTokenDTO(token, expirationDate);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsernameFromAuthHeader(String authHeader) {
        String token = authHeader.split(" ")[1];
        return extractUsername(token);
    }

}
