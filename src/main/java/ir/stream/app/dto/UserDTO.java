package ir.stream.app.dto;

import ir.stream.app.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String username;
    private String password;

    public UserDTO(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}