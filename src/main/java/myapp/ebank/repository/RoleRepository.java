package myapp.ebank.repository;

import myapp.ebank.model.Roles;
import myapp.ebank.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    List<Roles> findAllByisActive(boolean status);

}
