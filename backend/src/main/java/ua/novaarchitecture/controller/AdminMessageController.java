package ua.novaarchitecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.novaarchitecture.entity.ContactMessage;
import ua.novaarchitecture.repository.ContactMessageRepository;

import java.util.List;
import java.util.Map;

/**
 * Admin REST Controller for managing contact messages.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/messages")
@RequiredArgsConstructor
public class AdminMessageController {

    private final ContactMessageRepository messageRepository;

    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(messageRepository.findByIsArchivedFalseOrderByCreatedAtDesc());
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ContactMessage>> getUnreadMessages() {
        return ResponseEntity.ok(messageRepository.findByIsReadFalseOrderByCreatedAtDesc());
    }

    @GetMapping("/archived")
    public ResponseEntity<List<ContactMessage>> getArchivedMessages() {
        return ResponseEntity.ok(messageRepository.findByIsArchivedTrueOrderByCreatedAtDesc());
    }

    @GetMapping("/count/unread")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        long count = messageRepository.countByIsReadFalse();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getMessage(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ContactMessage> markAsRead(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    message.setIsRead(true);
                    ContactMessage updated = messageRepository.save(message);
                    log.info("Marked message {} as read", id);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<ContactMessage> archiveMessage(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    message.setIsArchived(true);
                    message.setIsRead(true);
                    ContactMessage updated = messageRepository.save(message);
                    log.info("Archived message {}", id);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unarchive")
    public ResponseEntity<ContactMessage> unarchiveMessage(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    message.setIsArchived(false);
                    ContactMessage updated = messageRepository.save(message);
                    log.info("Unarchived message {}", id);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/notes")
    public ResponseEntity<ContactMessage> updateNotes(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return messageRepository.findById(id)
                .map(message -> {
                    message.setNotes(body.get("notes"));
                    ContactMessage updated = messageRepository.save(message);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMessage(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    messageRepository.delete(message);
                    log.info("Deleted message {}", id);
                    return ResponseEntity.ok(Map.of("deleted", true));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
