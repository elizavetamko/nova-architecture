package ua.novaarchitecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.novaarchitecture.entity.ContactMessage;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    List<ContactMessage> findByIsArchivedFalseOrderByCreatedAtDesc();

    List<ContactMessage> findByIsReadFalseOrderByCreatedAtDesc();

    List<ContactMessage> findByIsArchivedTrueOrderByCreatedAtDesc();

    long countByIsReadFalse();
}
