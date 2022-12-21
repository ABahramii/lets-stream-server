package ir.stream.repository;

import ir.stream.core.repository.AbstractCrudRepository;
import ir.stream.entity.Role;

import java.util.Optional;

public interface RoleRepository extends AbstractCrudRepository<Role, Long> {

    Optional<Role> findByNameIgnoreCase(String name);
}
