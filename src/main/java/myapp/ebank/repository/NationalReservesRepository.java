package myapp.ebank.repository;

import myapp.ebank.model.entity.NationalReserves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface NationalReservesRepository  extends JpaRepository<NationalReserves , Long> {
    Optional<NationalReserves> findByDate(Date date);
}
