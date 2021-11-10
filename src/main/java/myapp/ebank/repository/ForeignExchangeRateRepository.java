package myapp.ebank.repository;

import myapp.ebank.model.entity.ForeignExchangeRates;
import myapp.ebank.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ForeignExchangeRateRepository extends JpaRepository<ForeignExchangeRates, Long> {
    @Query(value = "SELECT * FROM foreign_exchange_rates where date like CONCAT(:date,'%')", nativeQuery = true)
    List<ForeignExchangeRates> findByDateLike(Date date);

    @Query(value = "SELECT * from foreign_exchange_rates where date >= :startDate order by date asc", nativeQuery = true)
    List<ForeignExchangeRates> findByStartDateOrderByDateAsc(java.util.Date startDate);

    List<ForeignExchangeRates> findByDateBetweenOrderByDateDesc(java.util.Date startDate, java.util.Date endDate);

    List<Users> findAllByIsActiveOrderByCreatedDateDesc(boolean status);

}
