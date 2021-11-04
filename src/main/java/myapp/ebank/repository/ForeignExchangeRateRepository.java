package myapp.ebank.repository;

import myapp.ebank.model.entity.ForeignExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ForeignExchangeRateRepository extends JpaRepository<ForeignExchangeRates, Long> {
    Optional<ForeignExchangeRates> findByDate(Date Date);
}
