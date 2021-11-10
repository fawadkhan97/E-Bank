package myapp.ebank.service;

import myapp.ebank.model.entity.KiborRates;
import myapp.ebank.repository.KiborRepository;
import myapp.ebank.util.DateTime;
import myapp.ebank.util.SqlDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KiborService {

    private static final Logger log = LogManager.getLogger(KiborService.class);
    KiborRepository kiborRatesRepository;

    public KiborService(KiborRepository kiborRatesRepository) {
        // TODO Auto-generated constructor stub
        this.kiborRatesRepository = kiborRatesRepository;
    }

    /**
     * get daily kibor rates
     *
     * @return
     */
    public ResponseEntity<Object> dailyKiborRates() {
        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            Optional<KiborRates> kiborRates = kiborRatesRepository.findByDateLike(currentDate);
            if (kiborRates.isPresent()) {
                System.out.println("kibor rate is " + kiborRates.get().getBid());
                return new ResponseEntity<>(kiborRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("error has occured " + e.getMessage() + " " + e.getCause());
            log.debug("some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getDailyKiborRates ", e.getMessage());
            return new ResponseEntity<>("some error has occured ...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * get kibor Rate for specific Date
     *
     * @param date
     * @return
     */
    public ResponseEntity<Object> getKiborRateByDate(Date date) {
        try {
            Optional<KiborRates> Kibor = kiborRatesRepository.findByDateLike(date);
            if (Kibor.isPresent()) {
                return new ResponseEntity<>(Kibor, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getKiborRatesByDate ", e.getMessage());
            System.out.println("some error has occured " + e.getCause());
            return new ResponseEntity<Object>("an error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * find between specific date range by starting date
     *
     * @param startDate
     * @return
     */
    public ResponseEntity<Object> getKiborRateByStartDate(@RequestParam java.util.Date startDate) {
        try {
            Optional<KiborRates> kiborRate = kiborRatesRepository.findByStartDate(startDate);
            if (kiborRate.isPresent()) {
                return new ResponseEntity<>(kiborRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Kibor rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getRxchangeRatesByStartingDate ", e.getMessage());

            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * find between specific date range start and end
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public ResponseEntity<Object> getKiborRateBetweenDates(@RequestParam java.util.Date startDate, @RequestParam java.util.Date endDate) {
        try {
            List<KiborRates> kiborRate = kiborRatesRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
            if (!kiborRate.isEmpty()) {
                return new ResponseEntity<>(kiborRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Kibor rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getKiborRatesBetweenDates ", e.getMessage());
            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return List of kiborRates
     * @author Fawad khan
     */
    public ResponseEntity<Object> listAllKiborRates() {
        try {
            List<KiborRates> kiborRates = kiborRatesRepository.findAllByActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (kiborRates.isEmpty()) {
                return new ResponseEntity<>("  KiborRates are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(kiborRates, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("some error has occurred trying to Fetch kiborRates, in Class  KiborRatesService and its function listAllKiborRates ");
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("KiborRates could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * fetch record by id
     *
     * @param id
     * @return
     * @author fawad khan
     */
    public ResponseEntity<Object> getKiborRatesById(Long id) {
        try {
            Optional<KiborRates> kiborRate = kiborRatesRepository.findById(id);
            if (kiborRate.isPresent() && kiborRate.get().isActive()) {
                // check if kiborRate is verified
                log.info("kiborRate fetch and found from db by id  : ", kiborRate.toString());
                return new ResponseEntity<>(kiborRate, HttpStatus.FOUND);
            } else {
                log.info("no kiborRate found with id:", kiborRate.get().getId());
                return new ResponseEntity<>("could not found kiborRate with given details.... kiborRate may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.debug(
                    "some error has occurred during fetching KiborRates by id , in class KiborRatesService and its function getKiborRatesById ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("Unable to find KiborRates, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * save kibor rates
     *
     * @param kiborRates
     * @return
     */
    public ResponseEntity<Object> addKiborRate(KiborRates kiborRates) {
        try {
            kiborRatesRepository.save(kiborRates);
            return new ResponseEntity<>(kiborRates, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function get dailyKibor rates ", e.getMessage());
            System.out.println("error occured .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param kiborRate
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> updateKiborRate(KiborRates kiborRate) {
        try {
            kiborRate.setUpdatedDate(DateTime.getDateTime());
            kiborRatesRepository.save(kiborRate);
            return new ResponseEntity<>(kiborRate, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.debug(
                    "some error has occurred while trying to update kiborRate,, in class kiborRateService and its function updatekiborRate ",
                    e.getMessage());
            return new ResponseEntity<>("Kibor rates could not be updated , Data maybe incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> deleteKiborRate(Long id) {
        try {
            Optional<KiborRates> kiborRate = kiborRatesRepository.findById(id);
            if (kiborRate.isPresent()) {
                // set status false
                kiborRate.get().setActive(false);
                // set updated date
                java.util.Date date = DateTime.getDateTime();
                kiborRate.get().setUpdatedDate(date);
                kiborRatesRepository.save(kiborRate.get());
                return new ResponseEntity<>("KiborRates deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("KiborRates does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred while trying to Delete kiborRate,, in class kiborRateService and its function deletekiborRate ",
                    e.getMessage(), e.getCause());
            return new ResponseEntity<>("KiborRates could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
