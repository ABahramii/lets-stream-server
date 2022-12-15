package ir.stream.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private long id;
    private String name;
    private String username;
    private String password;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }
}
