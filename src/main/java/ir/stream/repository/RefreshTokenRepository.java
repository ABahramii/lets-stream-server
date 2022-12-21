package ir.stream.repository;

import ir.stream.core.repository.AbstractCrudRepository;
import ir.stream.entity.RefreshToken;
import ir.stream.entity.User;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends AbstractCrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByUUIDAndExpireAtIsAfterAndIsDeletedFalse(String uuid, Instant now);

}
