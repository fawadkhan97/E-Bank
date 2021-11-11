package myapp.ebank.repository;

import myapp.ebank.model.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loans, Long> {
    List<Loans> findAllByIsActiveOrderByCreatedDateDesc(boolean status);
    Optional<Loans> findByIdAndIsActive(long id, Boolean isActive);

}
