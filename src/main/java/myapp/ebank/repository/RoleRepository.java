package myapp.ebank.repository;

import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    List<Roles> findAllByActiveOrderByCreatedDateDesc(Boolean isActive);
    Optional<Roles> findByIdAndActive(long id, Boolean isActive);

}
