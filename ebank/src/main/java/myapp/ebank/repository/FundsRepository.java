package myapp.ebank.repository;

import myapp.ebank.model.Funds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundsRepository extends JpaRepository<Funds, Long> {
}
