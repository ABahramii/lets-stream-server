package ir.stream.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenDTO {
    private String accessToken;
    private long accessTokenExpireAt;
    private String username;
}
