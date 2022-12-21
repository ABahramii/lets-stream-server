package ir.stream.app.service;

import ir.stream.app.entity.Role;
import ir.stream.app.entity.User;
import ir.stream.app.entity.UserRole;
import ir.stream.core.service.AbstractCrudService;
import ir.stream.app.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService extends AbstractCrudService<UserRole, Long, UserRoleRepository> {

    public UserRoleService(UserRoleRepository userRoleRepository) {
        super(userRoleRepository);
    }

    public UserRole create(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setIsDeleted(false);
        return save(userRole);
    }
}
