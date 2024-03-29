package ir.stream.app.service;

import ir.stream.app.entity.RefreshToken;
import ir.stream.app.repository.RefreshTokenRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService extends AbstractService<RefreshToken, Long, RefreshTokenRepository> {

//    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        super(refreshTokenRepository);
//        this.refreshTokenRepository = refreshTokenRepository;
    }


    /*public RefreshToken generateRefreshToken(User user, Integer lifeTime) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByUser(user)
                .orElseGet(() -> create(user));

        refreshToken.setUUID(UUID.randomUUID().toString());
        refreshToken.setExpireAt(Instant.now().plus(lifeTime, ChronoUnit.MINUTES));
        return save(refreshToken);
    }*/

    /*public User getRefreshTokenUser(String uuid) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByUUIDAndExpireAtIsAfterAndIsDeletedFalse(uuid, Instant.now())
                .orElseThrow(
                        () -> new NotFoundException("Refresh token not found")
                );

        return refreshToken.getUser();
    }*/

    /*private RefreshToken create(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        return refreshToken;
    }*/


}
