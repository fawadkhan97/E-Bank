package myapp.ebank.repository;

import myapp.ebank.model.entity.KiborRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface KiborRepository extends JpaRepository<KiborRates, Long> {
    List<KiborRates> findAllByActiveOrderByCreatedDateDesc(boolean status);

    Optional<KiborRates> findByIdAndActive(long id, Boolean isActive);

    @Query(value = "SELECT * FROM kibor_rates where date like CONCAT(:date,'%')", nativeQuery = true)
    Optional<KiborRates> findByDateLike(Date date);

    @Query(value = "SELECT * from kibor_rates where date >= :startDate order by date desc", nativeQuery = true)
    Optional<KiborRates> findByStartDate(java.util.Date startDate);

    List<KiborRates> findByCreatedDateBetweenOrderByCreatedDateDesc(java.util.Date startDate, java.util.Date endDate);


}
