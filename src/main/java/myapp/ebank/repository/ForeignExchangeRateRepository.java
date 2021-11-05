package myapp.ebank.repository;

import myapp.ebank.model.entity.ForeignExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ForeignExchangeRateRepository extends JpaRepository<ForeignExchangeRates, Long> {
//    @Query(value = "SELECT * FROM ForeignExchangeRates  where date like "?1"",nativeQuery = true)
    Optional<ForeignExchangeRates> findByDateLike(Date date);

}
