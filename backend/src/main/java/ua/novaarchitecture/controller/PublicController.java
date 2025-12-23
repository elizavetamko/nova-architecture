package ua.novaarchitecture.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.novaarchitecture.entity.Project;
import ua.novaarchitecture.entity.ServiceItem;
import ua.novaarchitecture.entity.TeamMember;
import ua.novaarchitecture.repository.ProjectRepository;
import ua.novaarchitecture.repository.ServiceItemRepository;
import ua.novaarchitecture.repository.TeamMemberRepository;

import java.util.List;

/**
 * Public REST Controller for frontend data access (no auth required).
 */
@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamRepository;
    private final ServiceItemRepository serviceRepository;

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getActiveProjects() {
        return ResponseEntity.ok(projectRepository.findByActiveTrueOrderBySortOrderAsc());
    }

    @GetMapping("/projects/featured")
    public ResponseEntity<List<Project>> getFeaturedProjects() {
        return ResponseEntity.ok(projectRepository.findByFeaturedTrueAndActiveTrue());
    }

    @GetMapping("/team")
    public ResponseEntity<List<TeamMember>> getActiveTeamMembers() {
        return ResponseEntity.ok(teamRepository.findByActiveTrueOrderBySortOrderAsc());
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceItem>> getActiveServices() {
        return ResponseEntity.ok(serviceRepository.findByActiveTrueOrderBySortOrderAsc());
    }
}
