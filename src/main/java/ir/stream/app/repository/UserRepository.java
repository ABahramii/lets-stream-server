package ir.stream.app.repository;

import ir.stream.app.dto.UserDTO;
import ir.stream.app.entity.User;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends AbstractCrudRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);

    long countByUsername(String username);

    @Query("select user.UUID from User user where user.username=:username")
    Optional<String> findUUIDByUsername(String username);


    @Query("select new ir.stream.app.dto.UserDTO(user.name, user.username) from User user where user.UUID=:uuid")
    Optional<UserDTO> findUserDtoByUUID(String uuid);
}
