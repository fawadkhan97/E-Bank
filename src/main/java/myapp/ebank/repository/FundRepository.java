package myapp.ebank.repository;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.model.entity.Organizations;
import myapp.ebank.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FundRepository extends JpaRepository<Funds, Long> {
    List<Funds> findAllByActiveOrderByCreatedDateDesc(boolean status);

}
