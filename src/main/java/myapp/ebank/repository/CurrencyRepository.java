package myapp.ebank.repository;

import myapp.ebank.model.entity.Currencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currencies, Long> {
    List<Currencies> findAllByIsActiveOrderByCreatedDateDesc(boolean status);

}
