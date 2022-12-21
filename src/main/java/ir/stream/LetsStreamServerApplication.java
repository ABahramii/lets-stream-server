package ir.stream;

import ir.stream.entity.Role;
import ir.stream.entity.User;
import ir.stream.service.RoleService;
import ir.stream.service.UserRoleService;
import ir.stream.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class LetsStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetsStreamServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserService userService, RoleService roleService, UserRoleService userRoleService) {
        return args -> {
            User user1 = new User("amir", "java9", "123", new HashSet<>());
            User user2 = new User("james", "butterfly", "123", new HashSet<>());
            User user3 = new User("dan", "rtmp", "123", new HashSet<>());
            userService.create(user1);
            userService.create(user2);
            userService.create(user3);

            Role roleUser = new Role("ROLE_USER", new HashSet<>());
            Role roleManager = new Role("ROLE_MANAGER", new HashSet<>());
            Role roleAdmin = new Role("ROLE_ADMIN", new HashSet<>());
            Role roleSuperAdmin = new Role("ROLE_SUPER_ADMIN", new HashSet<>());
            roleService.create(roleUser);
            roleService.create(roleManager);
            roleService.create(roleAdmin);
            roleService.create(roleSuperAdmin);

            userRoleService.create(user1, roleSuperAdmin);
            userRoleService.create(user1, roleAdmin);
            userRoleService.create(user2, roleManager);
            userRoleService.create(user3, roleUser);
        };
    }

}
