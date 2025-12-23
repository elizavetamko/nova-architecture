package ua.novaarchitecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.novaarchitecture.entity.ServiceItem;
import ua.novaarchitecture.repository.ServiceItemRepository;

import java.util.List;
import java.util.Map;

/**
 * Admin REST Controller for managing services.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/services")
@RequiredArgsConstructor
public class AdminServiceController {

    private final ServiceItemRepository serviceRepository;

    @GetMapping
    public ResponseEntity<List<ServiceItem>> getAllServices() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ServiceItem>> getActiveServices() {
        return ResponseEntity.ok(serviceRepository.findByActiveTrueOrderBySortOrderAsc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItem> getService(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceItem> createService(@RequestBody ServiceItem service) {
        log.info("Creating service: {}", service.getTitle());
        ServiceItem saved = serviceRepository.save(service);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceItem> updateService(@PathVariable Long id, @RequestBody ServiceItem service) {
        return serviceRepository.findById(id)
                .map(existing -> {
                    service.setId(id);
                    service.setCreatedAt(existing.getCreatedAt());
                    ServiceItem updated = serviceRepository.save(service);
                    log.info("Updated service: {}", updated.getTitle());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteService(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    serviceRepository.delete(service);
                    log.info("Deleted service: {}", service.getTitle());
                    return ResponseEntity.ok(Map.of("deleted", true));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<ServiceItem> toggleActive(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setActive(!service.getActive());
                    ServiceItem updated = serviceRepository.save(service);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
