package ir.stream.app.rest;

import ir.stream.app.dto.AuthDTO;
import ir.stream.app.dto.AuthenticationTokenDTO;
import ir.stream.app.dto.UsernameTokenDTO;
import ir.stream.app.entity.User;
import ir.stream.app.service.UserService;
import ir.stream.app.utils.JwtUtils;
import ir.stream.core.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        User user = userService.findByUsername(authDTO.getUsername());
        if (user != null) {
            return ResponseEntity.ok(new HttpResponse<>(jwtUtils.generateToken(user)));
        }
        return ResponseEntity.badRequest().build();
    }

    /*@GetMapping("/token/refresh/{uuid}")
    public ResponseEntity<HttpResponse<AuthenticationTokenDTO>> getAccessTokenByRefreshToken(
            @PathVariable String uuid
    ) {
        AuthenticationTokenDTO authenticationTokenDTO = userService.refreshTokens(uuid);
        return ResponseEntity.ok(new HttpResponse<>(authenticationTokenDTO));
    }*/

    /*@GetMapping("/token/isValid/{token}")
    public ResponseEntity<HttpResponse<Map<String, Boolean>>> isTokenValid(@PathVariable String token) {
        try {
            String username = jwtUtils.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            boolean tokenValid = jwtUtils.isTokenValid(token, userDetails);
            return ResponseEntity.ok(new HttpResponse<>(Map.of("isTokenValid", tokenValid)));
        } catch (Exception e) {
            return ResponseEntity.ok(new HttpResponse<>(Map.of("isTokenValid", false)));
        }
    }*/

    @PostMapping("/token/isValid")
    public ResponseEntity<HttpResponse<Map<String, Boolean>>> checkTokenWithUsername(@RequestBody UsernameTokenDTO usernameTokenDTO) {
        String username = jwtUtils.extractUsername(usernameTokenDTO.getToken());
        boolean tokenValid = username.equals(usernameTokenDTO.getUsername()) && !jwtUtils.isTokenExpired(usernameTokenDTO.getToken());
        return ResponseEntity.ok(new HttpResponse<>(Map.of("isTokenValid", tokenValid)));
    }
}
