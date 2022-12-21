package ir.stream.service;

import ir.stream.core.exception.NotFoundException;
import ir.stream.core.service.AbstractCrudService;
import ir.stream.entity.Role;
import ir.stream.repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleService
        extends AbstractCrudService<Role, Long, RoleRepository> {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException(String.format("Role by name %s not found !", name)));
    }
}
