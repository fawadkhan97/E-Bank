package myapp.ebank.repository;

import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NationalReservesRepository extends JpaRepository<NationalReserves, Long> {
    @Query(value = "SELECT * FROM national_reserves where date like CONCAT(:date,'%') Order By date Desc ", nativeQuery = true)
    Optional<NationalReserves> findByDateLike(Date date);

    @Query(value = "SELECT * from national_reserves where date >= :startDate Order By date Desc", nativeQuery = true)
    Optional<NationalReserves> findByStartDate(java.util.Date startDate);

    @Query(value = "SELECT * from national_reserves where date BETWEEN :startDate AND :endDate Order By date Desc", nativeQuery = true)
    Optional<NationalReserves> findByStartAndEndDate(java.util.Date startDate, java.util.Date endDate);

    List<NationalReserves> findAllByIsActiveOrderByCreatedDateDesc(boolean status);

}
