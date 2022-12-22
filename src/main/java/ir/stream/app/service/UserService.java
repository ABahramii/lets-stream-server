package ir.stream.app.service;


import ir.stream.app.entity.User;
import ir.stream.app.repository.UserRepository;
import ir.stream.core.exception.NotFoundException;
import ir.stream.core.service.AbstractCrudService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public UserDetails loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
