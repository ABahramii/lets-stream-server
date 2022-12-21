package ir.stream.repository;

import ir.stream.core.repository.AbstractCrudRepository;
import ir.stream.entity.User;

import java.util.Optional;

public interface UserRepository extends AbstractCrudRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);
}
