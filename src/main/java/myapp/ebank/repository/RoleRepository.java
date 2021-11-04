package myapp.ebank.repository;

import myapp.ebank.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    List<Roles> findAllByisActive(boolean status);

}
