package ir.stream.core.repository;


import ir.stream.core.model.AbstractBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractCrudRepository<ENTITY extends AbstractBaseEntity<PK>, PK extends Serializable>
        extends JpaRepository<ENTITY, PK> {

    Optional<ENTITY> findByUUID(String uuid);
}
