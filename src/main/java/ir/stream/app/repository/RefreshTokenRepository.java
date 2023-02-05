package ir.stream.app.repository;

import ir.stream.app.entity.RefreshToken;
import ir.stream.core.repository.AbstractCrudRepository;

public interface RefreshTokenRepository extends AbstractCrudRepository<RefreshToken, Long> {

//    Optional<RefreshToken> findByUser(User user);

//    Optional<RefreshToken> findByUUIDAndExpireAtIsAfterAndIsDeletedFalse(String uuid, Instant now);

}
