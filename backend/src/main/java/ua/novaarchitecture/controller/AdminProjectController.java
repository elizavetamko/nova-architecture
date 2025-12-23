package ua.novaarchitecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.novaarchitecture.entity.Project;
import ua.novaarchitecture.repository.ProjectRepository;

import java.util.List;
import java.util.Map;

/**
 * Admin REST Controller for managing projects.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Project>> getActiveProjects() {
        return ResponseEntity.ok(projectRepository.findByActiveTrueOrderBySortOrderAsc());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Project>> getFeaturedProjects() {
        return ResponseEntity.ok(projectRepository.findByFeaturedTrueAndActiveTrue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        log.info("Creating project: {}", project.getTitle());
        Project saved = projectRepository.save(project);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return projectRepository.findById(id)
                .map(existing -> {
                    project.setId(id);
                    project.setCreatedAt(existing.getCreatedAt());
                    Project updated = projectRepository.save(project);
                    log.info("Updated project: {}", updated.getTitle());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProject(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    log.info("Deleted project: {}", project.getTitle());
                    return ResponseEntity.ok(Map.of("deleted", true));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<Project> toggleActive(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setActive(!project.getActive());
                    Project updated = projectRepository.save(project);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/toggle-featured")
    public ResponseEntity<Project> toggleFeatured(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setFeatured(!project.getFeatured());
                    Project updated = projectRepository.save(project);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
