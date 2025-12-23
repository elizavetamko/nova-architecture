package ua.novaarchitecture.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ua.novaarchitecture.entity.AdminUser;
import ua.novaarchitecture.model.LoginRequest;
import ua.novaarchitecture.model.LoginResponse;
import ua.novaarchitecture.repository.AdminUserRepository;
import ua.novaarchitecture.security.JwtService;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controller for authentication endpoints.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AdminUserRepository adminUserRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtService.generateToken(userDetails);

            // Update last login time
            AdminUser adminUser = adminUserRepository.findByUsername(request.getUsername())
                    .orElseThrow();
            adminUser.setLastLogin(LocalDateTime.now());
            adminUserRepository.save(adminUser);

            log.info("Login successful for user: {}", request.getUsername());

            return ResponseEntity.ok(LoginResponse.success(
                    token,
                    adminUser.getUsername(),
                    adminUser.getFullName(),
                    adminUser.getRole()
            ));

        } catch (BadCredentialsException e) {
            log.warn("Login failed for user: {} - Invalid credentials", request.getUsername());
            return ResponseEntity.status(401)
                    .body(LoginResponse.error("Невірний логін або пароль"));
        } catch (Exception e) {
            log.error("Login error for user: {} - {}", request.getUsername(), e.getMessage());
            return ResponseEntity.status(500)
                    .body(LoginResponse.error("Помилка авторизації"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);

            AdminUser adminUser = adminUserRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(Map.of(
                    "username", adminUser.getUsername(),
                    "fullName", adminUser.getFullName(),
                    "email", adminUser.getEmail() != null ? adminUser.getEmail() : "",
                    "role", adminUser.getRole(),
                    "lastLogin", adminUser.getLastLogin() != null ? adminUser.getLastLogin().toString() : ""
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            boolean isValid = jwtService.isTokenValid(token, userDetails);
            return ResponseEntity.ok(Map.of("valid", isValid));

        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("valid", false));
        }
    }
}
