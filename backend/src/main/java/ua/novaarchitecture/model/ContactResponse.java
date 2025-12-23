package ua.novaarchitecture.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * DTO for contact form responses.
 * Contains success status, message, and optional error details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponse {

    private boolean success;
    private String message;
    private Instant timestamp;
    private Map<String, String> errors;

    /**
     * Creates a success response.
     */
    public static ContactResponse success(String message) {
        return ContactResponse.builder()
                .success(true)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Creates an error response with validation errors.
     */
    public static ContactResponse error(String message, Map<String, String> errors) {
        return ContactResponse.builder()
                .success(false)
                .message(message)
                .timestamp(Instant.now())
                .errors(errors)
                .build();
    }

    /**
     * Creates a simple error response.
     */
    public static ContactResponse error(String message) {
        return ContactResponse.builder()
                .success(false)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }
}
