package ir.stream.app.service;


import ir.stream.app.dto.UserDTO;
import ir.stream.app.entity.User;
import ir.stream.app.repository.UserRepository;
import ir.stream.core.exception.DuplicateException;
import ir.stream.core.exception.NotFoundException;
import ir.stream.core.service.AbstractService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User, Long, UserRepository> implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException(String.format("User by username %s not found !", username)));
    }

    public User create(UserDTO userDTO) {
        if (canCreate(userDTO.getUsername())) {
            User user = new User();
            user.setName(userDTO.getName());
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return save(user);
        }
        throw new DuplicateException("username already taken.");
    }

    public void edit(UserDTO userDTO) {
        User user = findByUsername(userDTO.getUsername());
        if (user.getPassword().equals(passwordEncoder.encode(userDTO.getPreviousPass()))) {
            user.setName(userDTO.getName());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            save(user);
        } else {
            throw new NotFoundException("previous pass is incorrect");
        }
    }

    public User create(User user) {
        return save(user);
    }

    private boolean canCreate(String username) {
        return userRepository.countByUsername(username) == 0;
    }

    /*public AuthenticationTokenDTO refreshTokens(String refreshToken) {
        User user = refreshTokenService.getRefreshTokenUser(refreshToken);
        return jwtUtils.generateToken(user);
    }*/

    public String findUUIDByUsername(String username) {
        return userRepository.findUUIDByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User by username %s not found !", username)));
    }

    public UserDTO findUserDtoByUUID(String uuid) {
        return userRepository.findUserDtoByUUID(uuid)
                .orElseThrow(() -> new NotFoundException("User not found !"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
