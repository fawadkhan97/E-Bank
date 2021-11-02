package myapp.ebank.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.KiborRates;


@Repository
public interface KiborRepository extends JpaRepository<KiborRates, Long> {
	
	Optional<KiborRates> findByDate(Date date);

}
