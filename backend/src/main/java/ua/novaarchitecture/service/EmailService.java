package ua.novaarchitecture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.novaarchitecture.model.ContactRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service for sending emails.
 * Currently runs in SIMULATION mode - logs emails instead of sending.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${app.mail.admin-email}")
    private String adminEmail;

    @Value("${app.mail.simulation:true}")
    private boolean simulationMode;

    /**
     * Sends notification email to admin about new contact form submission.
     * In simulation mode, just logs the email content.
     */
    @Async
    public void sendContactNotification(ContactRequest request) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        if (simulationMode) {
            log.info("═══════════════════════════════════════════════════════════════");
            log.info("📧 EMAIL SIMULATION - Admin Notification");
            log.info("═══════════════════════════════════════════════════════════════");
            log.info("To: {}", adminEmail);
            log.info("Subject: Nova Architecture: Нове повідомлення від {}", request.getName());
            log.info("───────────────────────────────────────────────────────────────");
            log.info("Ім'я: {}", request.getName());
            log.info("Email: {}", request.getEmail());
            log.info("Телефон: {}", request.getPhone() != null ? request.getPhone() : "Не вказано");
            log.info("Дата: {}", timestamp);
            log.info("───────────────────────────────────────────────────────────────");
            log.info("Повідомлення:");
            log.info("{}", request.getMessage());
            log.info("═══════════════════════════════════════════════════════════════");
        } else {
            // Real email sending would go here
            log.info("Sending real email to admin for: {}", request.getEmail());
        }
    }

    /**
     * Sends confirmation email to the user.
     * In simulation mode, just logs the email content.
     */
    @Async
    public void sendUserConfirmation(ContactRequest request) {
        if (simulationMode) {
            log.info("═══════════════════════════════════════════════════════════════");
            log.info("📧 EMAIL SIMULATION - User Confirmation");
            log.info("═══════════════════════════════════════════════════════════════");
            log.info("To: {}", request.getEmail());
            log.info("Subject: Nova Architecture: Дякуємо за ваше звернення!");
            log.info("───────────────────────────────────────────────────────────────");
            log.info("Шановний(а) {},", request.getName());
            log.info("");
            log.info("Ми отримали ваше повідомлення і вдячні за інтерес до Nova Architecture.");
            log.info("Наші спеціалісти розглянуть ваше звернення та зв'яжуться з вами");
            log.info("найближчим часом.");
            log.info("");
            log.info("З повагою,");
            log.info("Команда Nova Architecture");
            log.info("═══════════════════════════════════════════════════════════════");
        } else {
            // Real email sending would go here
            log.info("Sending confirmation email to: {}", request.getEmail());
        }
    }
}
