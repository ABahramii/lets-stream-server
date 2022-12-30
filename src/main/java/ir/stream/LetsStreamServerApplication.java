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

            Room room = new Room("temp", user1, new HashSet<>(), new HashSet<>());
            roomService.save(room);

            Guest guest1 = new Guest("guest-1", room);
            Guest guest2 = new Guest("guest-2", room);
            guestService.save(guest1);
            guestService.save(guest2);

            RoomUser roomUser1 = new RoomUser(room, user1);
            RoomUser roomUser2 = new RoomUser(room, user2);
            RoomUser roomUser3 = new RoomUser(room, user3);
            roomUserService.save(roomUser1);
            roomUserService.save(roomUser2);
            roomUserService.save(roomUser3);

            Chat chat1 = new Chat("hello world, java is the king,,,", "12 AM", user2.getUsername(), true, room);
            Chat chat2 = new Chat("js is also good.", "12:13 AM", guest1.getName(), false, room);
            Chat chat3 = new Chat("but rust will overcome :)", "12:16 AM", user3.getUsername(), true, room);
            Chat chat4 = new Chat("ha ha ha", "12:16 AM", user1.getUsername(), true, room);
            Chat chat5 = new Chat("talk is cheap, show me the code", "12:30 AM", guest2.getName(), false, room);
            chatService.save(chat1);
            chatService.save(chat2);
            chatService.save(chat3);
            chatService.save(chat4);
            chatService.save(chat5);

        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
