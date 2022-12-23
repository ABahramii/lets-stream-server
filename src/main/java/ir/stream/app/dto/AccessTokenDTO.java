package ir.stream.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenDTO {
    private String token;
    private Date expireAt;
}
