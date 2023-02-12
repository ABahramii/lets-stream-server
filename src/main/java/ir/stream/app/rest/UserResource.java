package ir.stream.app.rest;

import ir.stream.app.dto.AuthenticationTokenDTO;
import ir.stream.app.dto.UserDTO;
import ir.stream.app.entity.User;
import ir.stream.app.service.RoleService;
import ir.stream.app.service.UserRoleService;
import ir.stream.app.service.UserService;
import ir.stream.app.utils.JwtUtils;
import ir.stream.core.dto.HttpResponse;
import ir.stream.core.dto.HttpResponseStatus;
import ir.stream.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /*@GetMapping
    public ResponseEntity<HttpResponse<List<UserDTO>>> findALl() {
        List<User> userList = userService.findAll();
        List<UserDTO> userDtoList = Mapper.userDtoMapper(userList);
        return ResponseEntity.ok(new HttpResponse<>(userDtoList));
    }*/

    @GetMapping("/findUUID")
    public ResponseEntity<HttpResponse<Map<String, String>>> findUUIDByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.split(" ")[1];
        String username = jwtUtils.extractUsername(token);
        if (!jwtUtils.isTokenExpired(token)) {
            String uuid = userService.findUUIDByUsername(username);
            return ResponseEntity.ok(new HttpResponse<>(Map.of("uuid", uuid)));
        }
        throw new NotFoundException("token is expired");
    }

    @GetMapping("/find/{uuid}")
    public ResponseEntity<HttpResponse<UserDTO>> findUser(@PathVariable String uuid) {
        return ResponseEntity.ok(new HttpResponse<>(userService.findUserDtoByUUID(uuid)));
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse<AuthenticationTokenDTO>> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        return ResponseEntity.ok(new HttpResponse<>(jwtUtils.generateToken(user)));
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpResponseStatus> editUser(@RequestBody UserDTO userDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String username = jwtUtils.extractUsernameFromAuthHeader(authHeader);
        if (username.equals(userDTO.getUsername())) {
            userService.edit(userDTO);
            return ResponseEntity.ok(new HttpResponseStatus("ok", 200));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /*@PostMapping("/role/create")
    public ResponseEntity<HttpResponse<Role>> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(new HttpResponse<>(roleService.create(role)));
    }*/


    /*@PostMapping("/role/addToUser")
    public ResponseEntity<HttpResponse<UserRole>> addRoleToUser(@RequestBody UserRoleDTO dto) {
        User user = userService.findByUsername(dto.getUsername());
        Role role = roleService.findByName(dto.getRoleName());

        UserRole userRole = userRoleService.create(user, role);
        return ResponseEntity.ok(new HttpResponse<>(userRole));
    }*/

}
