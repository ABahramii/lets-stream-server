package ir.stream.app.rest;

import ir.stream.app.dto.AuthDTO;
import ir.stream.app.service.UserService;
import ir.stream.app.utils.JwtUtils;
import ir.stream.core.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<HttpResponse<String>> authenticate(@RequestBody AuthDTO authDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
        );

        UserDetails user = userService.loadUserByUsername(authDTO.getUsername());
        if (user != null) {
            return ResponseEntity.ok(new HttpResponse<>(jwtUtils.generateToken(user)));
        }
        return ResponseEntity.badRequest().build();
    }
}
