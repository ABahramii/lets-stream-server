package ir.stream.core.service;


import ir.stream.core.exception.NotFoundException;
import ir.stream.core.model.AbstractBaseEntity;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public class AbstractCrudService<ENTITY extends AbstractBaseEntity<PK>,
        PK extends Serializable, REPOSITORY extends AbstractCrudRepository<ENTITY, PK>> implements CrudService<ENTITY, PK> {

    private final REPOSITORY abstractRepository;
    protected Class<ENTITY> entityClass;

    public AbstractCrudService(REPOSITORY abstractRepository) {
        this.abstractRepository = abstractRepository;
        if (getClass().getGenericSuperclass() == getClass().getGenericSuperclass()) {
            ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
            if (genericSuperClass != null && genericSuperClass.getActualTypeArguments() != null
                    && genericSuperClass.getActualTypeArguments().length > 0) {
                if (genericSuperClass.getActualTypeArguments()[0] instanceof Class) {
                    entityClass = (Class<ENTITY>) genericSuperClass.getActualTypeArguments()[0];
                }
            }
        }
    }

    @Override
    public Page<ENTITY> findAll(Pageable pageable) {
        return abstractRepository.findAll(pageable);
    }

    @Override
    public List<ENTITY> findAll() {
        return abstractRepository.findAll();
    }

    @Override
    public Page<ENTITY> findAllNotDeleted(Pageable pageable) {
        return abstractRepository.findAllByIsDeleted(false, pageable);
    }

    @Override
    public List<ENTITY> findAllNotDeleted() {
        return abstractRepository.findAllByIsDeleted(false);
    }

    @Override
    public ENTITY save(ENTITY object) {
        return abstractRepository.save(object);
    }


    @Override
    public Long countAll() {
        return abstractRepository.count();
    }

    @Override
    public ENTITY getById(PK id) {
        return abstractRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("object %d from %s not found", id, entityClass))
        );
    }

    @Override
    public void delete(ENTITY object) {
        abstractRepository.delete(object);
    }

    @Override
    public void deleteById(PK id) {
        ENTITY object = getById(id);
        abstractRepository.delete(object);
    }

    @Override
    public void safeDelete(ENTITY object) {
        object.setIsDeleted(true);
        save(object);
    }

    public ENTITY findByUUID(String uuid) {
        return abstractRepository.findByUUID(uuid)
                .orElseThrow(() -> new NotFoundException("object not found!"));
    }

    public ENTITY findByUUIDNotDeleted(String uuid) {
        return abstractRepository.findByUUIDAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("object not found!"));
    }

    @Override
    public void safeDeleteById(PK id) {
        ENTITY object = getById(id);
        object.setIsDeleted(true);
        save(object);
    }
}
