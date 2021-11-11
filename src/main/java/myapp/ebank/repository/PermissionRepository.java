package myapp.ebank.repository;

import myapp.ebank.model.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PermissionRepository extends JpaRepository<Permissions, Long> {
    List<Permissions> findAllByIsActiveOrderByCreatedDateDesc(boolean status);
    Optional<Permissions> findByIdAndIsActive(long id, Boolean isActive);


}
