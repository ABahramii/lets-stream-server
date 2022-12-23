package ir.stream.app.service;


import ir.stream.app.dto.AuthenticationTokenDTO;
import ir.stream.app.entity.User;
import ir.stream.app.repository.UserRepository;
import ir.stream.app.utils.JwtUtils;
import ir.stream.core.exception.NotFoundException;
import ir.stream.core.service.AbstractCrudService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<User, Long, UserRepository> implements UserDetailsService {
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, RefreshTokenService refreshTokenService, JwtUtils jwtUtils) {
        super(userRepository);
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException(String.format("User by username %s not found !", username)));
    }

    public User create(User user) {
        return save(user);
    }

    public AuthenticationTokenDTO refreshTokens(String refreshToken) {
        User user = refreshTokenService.getRefreshTokenUser(refreshToken);
        return jwtUtils.generateToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
