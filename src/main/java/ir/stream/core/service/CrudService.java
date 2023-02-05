package ir.stream.core.service;


import ir.stream.core.exception.NotFoundException;
import ir.stream.core.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudService<ENTITY extends AbstractBaseEntity<PK>, PK extends Serializable> {

    List<ENTITY> findAll();

    ENTITY save(ENTITY object);

    void delete(ENTITY object);

    void deleteById(PK id);

    ENTITY getById(PK id) throws NotFoundException;
}
