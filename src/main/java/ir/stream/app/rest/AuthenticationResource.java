package ir.stream.app.rest;

import ir.stream.app.dto.AuthDTO;
import ir.stream.app.dto.AuthenticationTokenDTO;
import ir.stream.app.entity.User;
import ir.stream.app.service.UserService;
import ir.stream.app.utils.JwtUtils;
import ir.stream.core.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<HttpResponse<AuthenticationTokenDTO>> authenticate(@RequestBody AuthDTO authDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
        );

//        UserDetails user = userService.loadUserByUsername(authDTO.getUsername());
        User user = userService.findByUsername(authDTO.getUsername());
        if (user != null) {
            return ResponseEntity.ok(new HttpResponse<>(jwtUtils.generateToken(user)));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/token/refresh/{uuid}")
    public ResponseEntity<HttpResponse<AuthenticationTokenDTO>> getAccessTokenByRefreshToken(
            @PathVariable String uuid
    ) {
        AuthenticationTokenDTO authenticationTokenDTO = userService.refreshTokens(uuid);
        return ResponseEntity.ok(new HttpResponse<>(authenticationTokenDTO));
    }

}
