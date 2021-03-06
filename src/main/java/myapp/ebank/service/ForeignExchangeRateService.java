package myapp.ebank.service;

import myapp.ebank.model.entity.ForeignExchangeRates;
import myapp.ebank.repository.ForeignExchangeRateRepository;
import myapp.ebank.util.DateTime;
import myapp.ebank.util.ResponseMapping;
import myapp.ebank.util.SqlDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * The type Foreign exchange rate service.
 */
@Service
public class ForeignExchangeRateService {


    private static final Logger log = LogManager.getLogger(ForeignExchangeRateService.class);
    /**
     * The Foreign exchange rate repository.
     */
    ForeignExchangeRateRepository foreignExchangeRateRepository;

    /**
     * Instantiates a new Foreign exchange rate service.
     *
     * @param foreignExchangeRateRepository the foreign exchange rate repository
     */
    public ForeignExchangeRateService(ForeignExchangeRateRepository foreignExchangeRateRepository) {
        this.foreignExchangeRateRepository = foreignExchangeRateRepository;
    }


    /**
     * Gets daily foreign exchange rate.
     *
     * @param httpServletRequest the http servlet request
     * @return the daily foreign exchange rate
     */
    public ResponseEntity<Object> getDailyForeignExchangeRate(HttpServletRequest httpServletRequest) {

        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            log.info("Date is  {}", currentDate);
            List<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findByDateLike(currentDate);
            if (!foreignExchangeRate.isEmpty()) {
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info("some error has occurred trying to Fetch exchange rates, in Class ForeignExchangeRateService and its function getDailyExchangeRates {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Gets foreign exchange rate by date.
     *
     * @param date               the date
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rate by date
     */
    public ResponseEntity<Object> getForeignExchangeRateByDate(Date date, HttpServletRequest httpServletRequest) {
        try {
            List<ForeignExchangeRates> foreignExchangeRates = foreignExchangeRateRepository.findByDateLike(date);
            if (!foreignExchangeRates.isEmpty()) {
                return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignExchangeRateService and its function getExchangeRatesByDate {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Gets foreign exchange rate by start date.
     *
     * @param startDate          the start date
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rate by start date
     */
    public ResponseEntity<Object> getForeignExchangeRateByStartDate(@RequestParam java.util.Date startDate, HttpServletRequest httpServletRequest) {
        try {
            List<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findByStartDateOrderByDateAsc(startDate);
            if (!foreignExchangeRate.isEmpty()) {
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get foreign rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignExchangeRateService and its function getForeignExchangeRatesByStartingDate {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets foreign exchange rate between dates.
     *
     * @param startDate          the start date
     * @param endDate            the end date
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rate between dates
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> getForeignExchangeRateBetweenDates(@RequestParam java.util.Date startDate, @RequestParam java.util.Date endDate, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            List<ForeignExchangeRates> foreignExchangeRates = foreignExchangeRateRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
            if (!foreignExchangeRates.isEmpty()) {
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.OK, "found today foreign exchange rates ", httpServletRequest.getRequestURI(), foreignExchangeRates), HttpStatus.OK);

            } else
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "Could not get foreign rate ...", httpServletRequest.getRequestURI(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignExchangeRateService and its function getForeignExchangeRatesBetweenDates {} ... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "an error has occurred ", httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * List all foreign exchange rates response entity.
     *
     * @return the response entity
     */
    public ResponseEntity<Object> listAllForeignExchangeRates() {
        try {
            List<ForeignExchangeRates> foreignExchangeRates = foreignExchangeRateRepository.findAllByIsActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (foreignExchangeRates.isEmpty()) {
                return new ResponseEntity<>("  ForeignExchangeRates are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
            }

        } catch (Exception e) {
            log.info("some error has occurred trying to Fetch foreignExchangeRates, in Class  ForeignExchangeRatesService and its function listAllForeignExchangeRates ");
            log.info("error is {} .... {}", e.getMessage(), e.getCause());

            return new ResponseEntity<>("ForeignExchangeRates could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Gets foreign exchange rates by id.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rates by id
     */
    public ResponseEntity<Object> getForeignExchangeRatesById(Long id, HttpServletRequest httpServletRequest) {
        try {
            Optional<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findById(id);
            if (foreignExchangeRate.isPresent() && foreignExchangeRate.get().isActive()) {
                log.info("foreignExchangeRate fetch and found from db by id  : {}", foreignExchangeRate.get().getId());
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.FOUND);
            } else {
                log.info("no foreignExchangeRate found with id: {}", id);
                return new ResponseEntity<>("could not found foreignExchangeRate with given details.... foreignExchangeRate may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching ForeignExchangeRates by id , in class ForeignExchangeRatesService and its function getForeignExchangeRatesById {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>("Unable to find ForeignExchangeRates, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Add foreign exchange rate response entity.
     *
     * @param foreignExchangeRates the foreign exchange rates
     * @param httpServletRequest   the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> addForeignExchangeRate(List<ForeignExchangeRates> foreignExchangeRates, HttpServletRequest httpServletRequest) {
        try {
            for (ForeignExchangeRates foreignExchangeRates1 : foreignExchangeRates) {
                foreignExchangeRates1.setActive(true);
            }
            foreignExchangeRateRepository.saveAll(foreignExchangeRates);
            return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            log.info("an error has occurred while adding rates...{} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>("data maybe incorrect please check again..", HttpStatus.NOT_ACCEPTABLE);
        } catch (DataIntegrityViolationException e) {
            log.info("exception is {}...{}", e.getCause(), e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignExchangeRateService and its function get daily Exchange rates{} .... {}", e.getMessage(), e.getCause());
            log.info("Is rollbackOnly: {}", TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
            return new ResponseEntity<>("some error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update foreign exchange rate response entity.
     *
     * @param foreignExchangeRates the foreign exchange rates
     * @param httpServletRequest   the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> updateForeignExchangeRate(List<ForeignExchangeRates> foreignExchangeRates, HttpServletRequest httpServletRequest) {
        try {
            for (ForeignExchangeRates foreignExchangeRates1 : foreignExchangeRates) {
                foreignExchangeRates1.setUpdatedDate(DateTime.getDateTime());
            }
            foreignExchangeRateRepository.saveAll(foreignExchangeRates);
            return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.info(
                    "some error has occurred while trying to update foreignExchangeRate,, in class foreignExchangeRateService and its function update foreignExchangeRate... , {} .... ,,{}", e.getMessage(), e.getCause());
            return new ResponseEntity<>("Foreign exchanges rate could not be updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete foreign exchange rate response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> deleteForeignExchangeRate(Long id, HttpServletRequest httpServletRequest) {
        try {
            Optional<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findById(id);
            if (foreignExchangeRate.isPresent()) {
                // set status false
                foreignExchangeRate.get().setActive(false);
                // set updated date
                java.util.Date date = DateTime.getDateTime();
                foreignExchangeRate.get().setUpdatedDate(date);
                foreignExchangeRateRepository.save(foreignExchangeRate.get());
                return new ResponseEntity<>(" ForeignExchangeRate deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>(" ForeignExchangeRate does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred while trying to Delete foreignExchangeRate,, in class foreignExchangeRateService and its function delete foreignExchangeRate... {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>("ForeignExchangeRate could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
