package ir.stream.app.utils;

import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class JWTUtil {

/*
    private final JWTProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;


    public AuthenticationTokenDTO generateAuthenticationToken(User user) {
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user, jwtProperties.getRefreshTokenLifeTime());
        AccessTokenDTO accessTokenDTO = generateAccessToken(user);

        return new AuthenticationTokenDTO(
                accessTokenDTO.getToken(),
                refreshToken.getUUID(),
                accessTokenDTO.getExpireAt().toEpochMilli(),
                refreshToken.getExpireAt().toEpochMilli()
        );

    }

    public UserCredentials extractUserCredentials(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        String uuid = decodedJWT.getSubject();
        Set<SimpleGrantedAuthority> authorities = Arrays
                .stream(decodedJWT.getClaim("roles").asArray(String.class))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UserCredentials(uuid, authorities);
    }

    private DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private AccessTokenDTO generateAccessToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes());
        Instant accessTokenExpireAt = Instant.now().plus(jwtProperties.getAccessTokenLifeTime(), ChronoUnit.MINUTES);
        String accessToken = JWT.create()
                .withSubject(user.getUUID())
                .withExpiresAt(Date.from(accessTokenExpireAt))
                .withClaim("roles", user.getRoles().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        return new AccessTokenDTO(accessToken, accessTokenExpireAt);
    }
*/


}
