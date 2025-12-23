package ua.novaarchitecture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.novaarchitecture.entity.ContactMessage;
import ua.novaarchitecture.model.ContactRequest;
import ua.novaarchitecture.model.ContactResponse;
import ua.novaarchitecture.repository.ContactMessageRepository;

/**
 * Service for processing contact form submissions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final EmailService emailService;
    private final ContactMessageRepository messageRepository;

    private static final String SUCCESS_MESSAGE =
            "Дякуємо! Ваше повідомлення надіслано. Ми зв'яжемося з вами найближчим часом.";

    /**
     * Processes a contact form submission.
     * Saves to database, sends notification to admin and confirmation to user.
     */
    public ContactResponse processContactRequest(ContactRequest request) {
        log.info("Processing contact request from: {} ({})", request.getName(), request.getEmail());

        try {
            // Save to database
            ContactMessage message = ContactMessage.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .message(request.getMessage())
                    .isRead(false)
                    .isArchived(false)
                    .build();

            messageRepository.save(message);
            log.info("Contact message saved with ID: {}", message.getId());

            // Send notification to admin
            emailService.sendContactNotification(request);

            // Send confirmation to user
            emailService.sendUserConfirmation(request);

            log.info("Contact request processed successfully for: {}", request.getEmail());
            return ContactResponse.success(SUCCESS_MESSAGE);
        } catch (Exception e) {
            log.error("Error processing contact request: {}", e.getMessage(), e);
            return ContactResponse.error("Помилка відправки. Спробуйте пізніше.");
        }
    }
}
