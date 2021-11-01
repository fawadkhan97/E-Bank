package myapp.ebank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.Currencies;

@Repository
public interface CurrencyRepository extends JpaRepository<Currencies, Long> {
	
	List<Currencies> findAllByisActive(boolean status);

}
