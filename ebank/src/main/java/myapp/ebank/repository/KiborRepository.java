package myapp.ebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.Kibor;


@Repository
public interface KiborRepository extends JpaRepository<Kibor, Long> {
	
	Optional<Kibor> findByDate(String date);

}
