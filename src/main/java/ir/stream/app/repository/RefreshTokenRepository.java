package ir.stream.app.repository;

import ir.stream.app.entity.User;
import ir.stream.core.repository.AbstractCrudRepository;
import ir.stream.app.entity.RefreshToken;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends AbstractCrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByUUIDAndExpireAtIsAfterAndIsDeletedFalse(String uuid, Instant now);

}
