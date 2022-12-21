package ir.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenDTO {
    private String token;
    private Instant expireAt;
}
