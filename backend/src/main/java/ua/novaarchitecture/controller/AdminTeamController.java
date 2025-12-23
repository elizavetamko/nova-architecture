package ua.novaarchitecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.novaarchitecture.entity.TeamMember;
import ua.novaarchitecture.repository.TeamMemberRepository;

import java.util.List;
import java.util.Map;

/**
 * Admin REST Controller for managing team members.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/team")
@RequiredArgsConstructor
public class AdminTeamController {

    private final TeamMemberRepository teamRepository;

    @GetMapping
    public ResponseEntity<List<TeamMember>> getAllMembers() {
        return ResponseEntity.ok(teamRepository.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TeamMember>> getActiveMembers() {
        return ResponseEntity.ok(teamRepository.findByActiveTrueOrderBySortOrderAsc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMember> getMember(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeamMember> createMember(@RequestBody TeamMember member) {
        log.info("Creating team member: {}", member.getName());
        TeamMember saved = teamRepository.save(member);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMember> updateMember(@PathVariable Long id, @RequestBody TeamMember member) {
        return teamRepository.findById(id)
                .map(existing -> {
                    member.setId(id);
                    member.setCreatedAt(existing.getCreatedAt());
                    TeamMember updated = teamRepository.save(member);
                    log.info("Updated team member: {}", updated.getName());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMember(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(member -> {
                    teamRepository.delete(member);
                    log.info("Deleted team member: {}", member.getName());
                    return ResponseEntity.ok(Map.of("deleted", true));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<TeamMember> toggleActive(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(member -> {
                    member.setActive(!member.getActive());
                    TeamMember updated = teamRepository.save(member);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
