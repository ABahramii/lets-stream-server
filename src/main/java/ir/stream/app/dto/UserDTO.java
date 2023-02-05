package ir.stream.app.dto;

import ir.stream.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String username;
    private String password;

    // Todo: remove
    public UserDTO(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
