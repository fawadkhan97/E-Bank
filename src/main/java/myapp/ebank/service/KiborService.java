package myapp.ebank.service;

import myapp.ebank.model.entity.KiborRates;
import myapp.ebank.repository.KiborRepository;
import myapp.ebank.util.DateTime;
import myapp.ebank.util.ResponseMapping;
import myapp.ebank.util.SqlDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * The type Kibor service.
 */
@Service
public class KiborService {

    private static final Logger log = LogManager.getLogger(KiborService.class);
    /**
     * The Kibor rates repository.
     */
    KiborRepository kiborRatesRepository;

    /**
     * Instantiates a new Kibor service.
     *
     * @param kiborRatesRepository the kibor rates repository
     */
    public KiborService(KiborRepository kiborRatesRepository) {
        // TODO Auto-generated constructor stub
        this.kiborRatesRepository = kiborRatesRepository;
    }

    /**
     * Daily kibor rates response entity.
     *
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> dailyKiborRates(HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            Optional<KiborRates> kiborRates = kiborRatesRepository.findByDateLike(currentDate);
            if (kiborRates.isPresent()) {
                System.out.println("kibor rate is " + kiborRates.get().getBid());
                return new ResponseEntity<>(kiborRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "\"Could not get today rate...", httpServletRequest.getRequestURI(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("error has occured " + e.getMessage() + " " + e.getCause());
            log.info("some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getDailyKiborRates " + e.getMessage());
                        return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "an error has occurred ", httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Gets kibor rate by date.
     *
     * @param date               the date
     * @param httpServletRequest the http servlet request
     * @return the kibor rate by date
     */
    public ResponseEntity<Object> getKiborRateByDate(Date date, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Optional<KiborRates> Kibor = kiborRatesRepository.findByDateLike(date);
            if (Kibor.isPresent()) {
                return new ResponseEntity<>(Kibor, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getKiborRatesByDate " + e.getMessage());
                        return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "an error has occurred ", httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Gets kibor rate by start date.
     *
     * @param startDate          the start date
     * @param httpServletRequest the http servlet request
     * @return the kibor rate by start date
     */
    public ResponseEntity<Object> getKiborRateByStartDate(@RequestParam java.util.Date startDate, HttpServletRequest httpServletRequest) {
        try {
            Optional<KiborRates> kiborRate = kiborRatesRepository.findByStartDate(startDate);
            if (kiborRate.isPresent()) {
                return new ResponseEntity<>(kiborRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Kibor rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getRxchangeRatesByStartingDate " + e.getMessage());

            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets kibor rate between dates.
     *
     * @param startDate          the start date
     * @param endDate            the end date
     * @param httpServletRequest the http servlet request
     * @return the kibor rate between dates
     */
    public ResponseEntity<Object> getKiborRateBetweenDates(@RequestParam java.util.Date startDate, @RequestParam java.util.Date endDate, HttpServletRequest httpServletRequest) {
        try {
            List<KiborRates> kiborRate = kiborRatesRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
            if (!kiborRate.isEmpty()) {
                return new ResponseEntity<>(kiborRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Kibor rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function getKiborRatesBetweenDates " + e.getMessage());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * List all kibor rates response entity.
     *
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> listAllKiborRates(HttpServletRequest httpServletRequest) throws ParseException {
        try {
            List<KiborRates> kiborRates = kiborRatesRepository.findAllByIsActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (kiborRates.isEmpty()) {
                return new ResponseEntity<>("  KiborRates are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(kiborRates, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("some error has occurred trying to Fetch kiborRates, in Class  KiborRatesService and its function listAllKiborRates " + e.getMessage());

            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "an error has occurred ", httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Gets kibor rates by id.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the kibor rates by id
     */
    public ResponseEntity<Object> getKiborRatesById(Long id, HttpServletRequest httpServletRequest) {
        try {
            Optional<KiborRates> kiborRate = kiborRatesRepository.findById(id);
            if (kiborRate.isPresent() && kiborRate.get().isActive()) {
                // check if kiborRate is verified
                log.info("kiborRate fetch and found from db by id  : "+ kiborRate.toString());
                return new ResponseEntity<>(kiborRate, HttpStatus.FOUND);
            } else {
                log.info("no kiborRate found with id:"+ kiborRate.get().getId());
                return new ResponseEntity<>("could not found kiborRate with given details.... kiborRate may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching KiborRates by id , in class KiborRatesService and its function getKiborRatesById "+
                    e.getMessage());
            return new ResponseEntity<>("Unable to find KiborRates, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Add kibor rate response entity.
     *
     * @param kiborRates         the kibor rates
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> addKiborRate(KiborRates kiborRates, HttpServletRequest httpServletRequest) {
        try {
            kiborRates.setActive(true);
            kiborRatesRepository.save(kiborRates);
            return new ResponseEntity<>(kiborRates, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch Kibor rates, in Class KiborRateService and its function get dailyKibor rates "+ e.getMessage());
            return new ResponseEntity<>("some error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update kibor rate response entity.
     *
     * @param kiborRate          the kibor rate
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> updateKiborRate(KiborRates kiborRate, HttpServletRequest httpServletRequest) {
        try {
            kiborRate.setUpdatedDate(DateTime.getDateTime());
            kiborRatesRepository.save(kiborRate);
            return new ResponseEntity<>(kiborRate, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.info(
                    "some error has occurred while trying to update kiborRate,, in class kiborRateService and its function updatekiborRate "+
                    e.getMessage());
            return new ResponseEntity<>("Kibor rates could not be updated , Data maybe incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete kibor rate response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> deleteKiborRate(Long id, HttpServletRequest httpServletRequest) {
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
            log.info(
                    "some error has occurred while trying to Delete kiborRate,, in class kiborRateService and its function deletekiborRate "+
                    e.getMessage()+ e.getCause());
            return new ResponseEntity<>("KiborRates could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
