package ir.stream.core.repository;


import ir.stream.core.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractCrudRepository<ENTITY extends BaseEntity<PK>, PK extends Serializable>
        extends JpaRepository<ENTITY, PK> {

    Page<ENTITY> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
    List<ENTITY> findAllByIsDeleted(Boolean isDeleted);
    Optional<ENTITY> findByUUID(String uuid);
    Optional<ENTITY> findByUUIDAndIsDeletedFalse(String uuid);

}
