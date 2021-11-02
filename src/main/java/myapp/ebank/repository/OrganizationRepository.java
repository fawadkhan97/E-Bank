package myapp.ebank.repository;

import myapp.ebank.model.Organizations;
import myapp.ebank.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organizations, Long> {

    List<Organizations> findAllByisActive(boolean status);

}
