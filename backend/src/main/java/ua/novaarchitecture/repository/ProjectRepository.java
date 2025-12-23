package ua.novaarchitecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.novaarchitecture.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByActiveTrue();

    List<Project> findByActiveTrueOrderBySortOrderAsc();

    List<Project> findByFeaturedTrueAndActiveTrue();

    List<Project> findByCategoryAndActiveTrue(String category);

    List<Project> findByActiveTrueAndCategoryOrderBySortOrderAsc(String category);
}
