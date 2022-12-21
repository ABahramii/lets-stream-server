package ir.stream.app.repository;

import ir.stream.app.entity.User;
import ir.stream.core.repository.AbstractCrudRepository;

import java.util.Optional;

public interface UserRepository extends AbstractCrudRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);
}
