package ua.novaarchitecture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for login responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private boolean success;
    private String token;
    private String username;
    private String fullName;
    private String role;
    private String message;

    public static LoginResponse success(String token, String username, String fullName, String role) {
        return LoginResponse.builder()
                .success(true)
                .token(token)
                .username(username)
                .fullName(fullName)
                .role(role)
                .message("Login successful")
                .build();
    }

    public static LoginResponse error(String message) {
        return LoginResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
