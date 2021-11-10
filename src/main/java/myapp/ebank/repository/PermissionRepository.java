package myapp.ebank.repository;

import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.model.entity.Permissions;
import myapp.ebank.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PermissionRepository extends JpaRepository<Permissions, Long> {
    List<Permissions> findAllByActiveOrderByCreatedDateDesc(boolean status);
    Optional<Permissions> findByIdAndActive(long id, Boolean isActive);


}
