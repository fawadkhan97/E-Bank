package myapp.ebank.repository;

import myapp.ebank.model.NationalReserves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NationalReservesRepository  extends JpaRepository<NationalReserves , Long> {
    Optional<NationalReserves> findByDate(String Date);
}
