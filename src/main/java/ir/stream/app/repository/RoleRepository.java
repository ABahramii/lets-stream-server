package ir.stream.app.repository;

import ir.stream.app.entity.Role;
import ir.stream.core.repository.AbstractCrudRepository;

import java.util.Optional;

public interface RoleRepository extends AbstractCrudRepository<Role, Long> {

    Optional<Role> findByNameIgnoreCase(String name);
}
