package ir.stream;

import ir.stream.app.entity.*;
import ir.stream.app.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
public class LetsStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetsStreamServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserService userService, RoleService roleService, UserRoleService userRoleService,
                                 RoomService roomService, GuestService guestService, RoomUserService roomUserService,
                                 ChatService chatService) {
        PasswordEncoder passwordEncoder = passwordEncoder();

        return args -> {
            User user1 = new User("amir", "java", passwordEncoder.encode("123"), new HashSet<>());
            User user2 = new User("james", "butterfly", passwordEncoder.encode("123"), new HashSet<>());
            User user3 = new User("dan", "rtmp", passwordEncoder.encode("123"), new HashSet<>());
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

            Room room0 = new Room("temp", user1, true, null, false, new HashSet<>(), new HashSet<>());
            Room room1 = new Room("temp1", user1,  true, null, false, new HashSet<>(), new HashSet<>());
            Room room2 = new Room("temp2", user2,  true, null, false, new HashSet<>(), new HashSet<>());
            Room room3 = new Room("temp3", user3,  true, null, false, new HashSet<>(), new HashSet<>());

            roomService.save(room0);
            roomService.save(room1);
            roomService.save(room2);
            roomService.save(room3);

            Guest guest1 = new Guest("guest-1", room0);
            guestService.save(guest1);

            RoomUser roomUser2 = new RoomUser(room0, user2);
            roomUserService.save(roomUser2);

            Chat chat1 = new Chat("This is chatroom", "12 AM", user2.getUsername(), true, room0);
            chatService.save(chat1);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
