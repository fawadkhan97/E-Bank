package myapp.ebank.repository;

import myapp.ebank.model.entity.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organizations, Long> {
    List<Organizations> findAllByIsActiveOrderByCreatedDateDesc(boolean status);
    Optional<Organizations> findByIdAndIsActive(long id, Boolean isActive);
}
