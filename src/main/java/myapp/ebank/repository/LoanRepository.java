package myapp.ebank.repository;

import myapp.ebank.model.entity.Loans;
import myapp.ebank.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loans, Long> {
    List<Loans> findAllByIsActiveOrderByCreatedDateDesc(boolean status);

}
