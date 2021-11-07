package myapp.ebank.repository;

import java.sql.Date;
import java.util.List;

import myapp.ebank.model.entity.ForeignExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.entity.Currencies;

@Repository
public interface CurrencyRepository extends JpaRepository<Currencies, Long> {
	List<Currencies> findAllByisActive(boolean status);
}
