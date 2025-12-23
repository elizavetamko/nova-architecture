package ua.novaarchitecture.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.novaarchitecture.model.ContactRequest;
import ua.novaarchitecture.model.ContactResponse;
import ua.novaarchitecture.service.ContactService;

/**
 * REST Controller for handling contact form submissions.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    /**
     * Handles contact form submission.
     *
     * @param request The contact form data
     * @return Response indicating success or failure
     */
    @PostMapping("/contact")
    public ResponseEntity<ContactResponse> submitContact(@Valid @RequestBody ContactRequest request) {
        log.info("Received contact request from: {}", request.getEmail());

        ContactResponse response = contactService.processContactRequest(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
