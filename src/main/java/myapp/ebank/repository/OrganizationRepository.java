package myapp.ebank.repository;

import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.model.entity.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organizations, Long> {
    List<Organizations> findAllByActiveOrderByCreatedDateDesc(boolean status);
    Optional<Organizations> findByIdAndActive(long id, Boolean isActive);

}
