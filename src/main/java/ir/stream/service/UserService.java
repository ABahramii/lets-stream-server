package ir.stream.service;


import ir.stream.core.exception.NotFoundException;
import ir.stream.core.service.AbstractCrudService;
import ir.stream.entity.User;
import ir.stream.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<User, Long, UserRepository> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException(String.format("User by username %s not found !", username)));
    }

    public User create(User user) {
        return save(user);
    }

}
