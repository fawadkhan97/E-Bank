package myapp.ebank.repository;

import myapp.ebank.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    List<Roles> findAllByIsActiveOrderByCreatedDateDesc(Boolean isActive);
    Optional<Roles> findByIdAndIsActive(long id, Boolean isActive);

}
