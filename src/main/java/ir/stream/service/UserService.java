package ir.stream.service;


import ir.stream.core.service.AbstractCrudService;
import ir.stream.entity.User;
import ir.stream.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends AbstractCrudService<User, Long, UserRepository> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return save(user);
    }

    public void addRoleToUser(String username, String roleName) {

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
