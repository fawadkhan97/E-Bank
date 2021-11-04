package myapp.ebank.repository;

import myapp.ebank.model.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PermissionRepository extends JpaRepository<Permissions, Long> {

    List<Permissions> findAllByisActive(boolean status);



}
