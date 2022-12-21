package ir.stream.rest;

import ir.stream.core.dto.HttpResponse;
import ir.stream.dto.UserDTO;
import ir.stream.dto.UserRoleDTO;
import ir.stream.entity.Role;
import ir.stream.entity.User;
import ir.stream.entity.UserRole;
import ir.stream.service.RoleService;
import ir.stream.service.UserRoleService;
import ir.stream.service.UserService;
import ir.stream.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<HttpResponse<List<UserDTO>>> findALl() {
        List<User> userList = userService.findAll();
        List<UserDTO> userDtoList = Mapper.userDtoMapper(userList);
        return ResponseEntity.ok(new HttpResponse<>(userDtoList));
    }

    @PostMapping("/user/create")
    public ResponseEntity<HttpResponse<User>> createUser(@RequestBody User user) {
        return ResponseEntity.ok(new HttpResponse<>(userService.create(user)));
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
