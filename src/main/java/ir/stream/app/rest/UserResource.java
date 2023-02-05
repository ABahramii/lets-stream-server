package ir.stream.app.rest;

import ir.stream.app.dto.AuthenticationTokenDTO;
import ir.stream.app.dto.UserDTO;
import ir.stream.app.dto.UserRoleDTO;
import ir.stream.app.entity.Role;
import ir.stream.app.entity.User;
import ir.stream.app.entity.UserRole;
import ir.stream.app.service.RoleService;
import ir.stream.app.service.UserRoleService;
import ir.stream.app.service.UserService;
import ir.stream.app.utils.JwtUtils;
import ir.stream.app.utils.Mapper;
import ir.stream.core.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<HttpResponse<List<UserDTO>>> findALl() {
        List<User> userList = userService.findAll();
        List<UserDTO> userDtoList = Mapper.userDtoMapper(userList);
        return ResponseEntity.ok(new HttpResponse<>(userDtoList));
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse<AuthenticationTokenDTO>> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        return ResponseEntity.ok(new HttpResponse<>(jwtUtils.generateToken(user)));
    }

    @PostMapping("/role/create")
    public ResponseEntity<HttpResponse<Role>> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(new HttpResponse<>(roleService.create(role)));
    }


    @PostMapping("/role/addToUser")
    public ResponseEntity<HttpResponse<UserRole>> addRoleToUser(@RequestBody UserRoleDTO dto) {
        User user = userService.findByUsername(dto.getUsername());
        Role role = roleService.findByName(dto.getRoleName());

        UserRole userRole = userRoleService.create(user, role);
        return ResponseEntity.ok(new HttpResponse<>(userRole));
    }

}
