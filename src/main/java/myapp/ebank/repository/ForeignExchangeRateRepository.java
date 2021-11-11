package myapp.ebank.repository;

import myapp.ebank.model.entity.ForeignExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ForeignExchangeRateRepository extends JpaRepository<ForeignExchangeRates, Long> {
    @Query(value = "SELECT * FROM foreign_exchange_rates where created_date like CONCAT(:date,'%')", nativeQuery = true)
    List<ForeignExchangeRates> findByDateLike(Date date);

    @Query(value = "SELECT * from foreign_exchange_rates where created_date >= :startDate order by created_date asc", nativeQuery = true)
    List<ForeignExchangeRates> findByStartDateOrderByDateAsc(java.util.Date startDate);

    List<ForeignExchangeRates> findByCreatedDateBetweenOrderByCreatedDateDesc(java.util.Date startDate, java.util.Date endDate);

    List<ForeignExchangeRates> findAllByIsActiveOrderByCreatedDateDesc(boolean status);

}
