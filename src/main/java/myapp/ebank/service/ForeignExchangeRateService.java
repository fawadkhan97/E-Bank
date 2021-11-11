package myapp.ebank.service;

import myapp.ebank.model.entity.ForeignExchangeRates;
import myapp.ebank.repository.ForeignExchangeRateRepository;
import myapp.ebank.util.DateTime;
import myapp.ebank.util.SqlDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ForeignExchangeRateService {


    private static final Logger log = LogManager.getLogger(ForeignExchangeRateService.class);
    ForeignExchangeRateRepository foreignExchangeRateRepository;

    public ForeignExchangeRateService(ForeignExchangeRateRepository foreignExchangeRateRepository) {
        this.foreignExchangeRateRepository = foreignExchangeRateRepository;
    }


    /**
     * get daily foreignExchange rate
     *
     * @return ForeignExchangeRate Object
     */
    public ResponseEntity<Object> getDailyForeignExchangeRate() {

        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            System.out.println("Date is " + currentDate);
            List<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findByDateLike(currentDate);
            if (!foreignExchangeRate.isEmpty()) {
                //  System.out.println("foreignExchange rate is " + foreignExchangeRate.getCurrency() + " " + foreignExchangeRate.get().getBuying());
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.debug("some error has occurred trying to Fetch exchange rates, in Class ForeignexchangeRateService and its function getDailyExchangeRates ", e.getMessage());
            System.out.println("some error has occurred " + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * get foreignExchange Rate for specific Date
     *
     * @param date
     * @return foreign exchange list
     */
    public ResponseEntity<Object> getForeignExchangeRateByDate(Date date) {
        try {
            List<ForeignExchangeRates> foreignExchangeRates = foreignExchangeRateRepository.findByDateLike(date);
            if (!foreignExchangeRates.isEmpty()) {
                return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignexchangeRateService and its function getExchangeRatesByDate ", e.getMessage());
            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * find between specific date range by starting date
     *
     * @param startDate
     * @return
     */
    public ResponseEntity<Object> getForeignExchangeRateByStartDate(@RequestParam java.util.Date startDate) {
        try {
            List<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findByStartDateOrderByDateAsc(startDate);
            if (!foreignExchangeRate.isEmpty()) {
                //        System.out.println("foreignExchange rate is " + foreignExchangeRate.get().getCurrency() + " " + foreignExchangeRate.get().getBuying());
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get foreign rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignexchangeRateService and its function getRxchangeRatesByStartingDate ", e.getMessage());
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
    public ResponseEntity<Object> getForeignExchangeRateBetweenDates(@RequestParam java.util.Date startDate, @RequestParam java.util.Date endDate) {
        try {
            List<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
            if (!foreignExchangeRate.isEmpty()) {
                //        System.out.println("foreignExchange rate is " + foreignExchangeRate.get().getCurrency() + " " + foreignExchangeRate.get().getBuying());
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get foreign rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignexchangeRateService and its function getForeignExchangeRatesBetweenDates ", e.getMessage());
            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return List of foreignExchangeRates
     * @author Fawad khan
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
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("ForeignExchangeRates could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * fetch record by id
     *
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> getForeignExchangeRatesById(Long id) {
        try {
            Optional<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findById(id);
            if (foreignExchangeRate.isPresent() && foreignExchangeRate.get().isActive()) {
                // check if foreignExchangeRate is verified
                log.info("foreignExchangeRate fetch and found from db by id  : ", foreignExchangeRate.toString());
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.FOUND);
            } else {
                log.info("no foreignExchangeRate found with id:", foreignExchangeRate.get().getId());
                return new ResponseEntity<>("could not found foreignExchangeRate with given details.... foreignExchangeRate may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.debug(
                    "some error has occurred during fetching ForeignExchangeRates by id , in class ForeignExchangeRatesService and its function getForeignExchangeRatesById ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("Unable to find ForeignExchangeRates, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * save foreignExchange rate
     *
     * @param foreignExchangeRates
     * @return
     */
    public ResponseEntity<Object> addForeignExchangeRate(List<ForeignExchangeRates> foreignExchangeRates) {
        try {
            for (ForeignExchangeRates foreignExchangeRates1 : foreignExchangeRates) {
              //  foreignExchangeRates1.setCreatedDate(DateTime.getDateTime());
                foreignExchangeRates1.setActive(true);
            }
            foreignExchangeRateRepository.saveAll(foreignExchangeRates);
            return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            log.debug("an error has occured while adding rates...", e.getMessage(), e.getCause());
            return new ResponseEntity<>("data maybe incorrect please check again..", HttpStatus.NOT_ACCEPTABLE);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch exchange rates, in Class ForeignexchangeRateService and its function get dailyexchange rates ", e.getMessage());
            log.debug("Is rollbackOnly: " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());

            System.out.println("error occurred .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * update rates
     *
     * @param foreignExchangeRates
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> updateForeignExchangeRate(List<ForeignExchangeRates> foreignExchangeRates) {
        try {
            for (ForeignExchangeRates foreignExchangeRates1 : foreignExchangeRates) {
                foreignExchangeRates1.setUpdatedDate(DateTime.getDateTime());
            }
            foreignExchangeRateRepository.saveAll(foreignExchangeRates);
            return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.debug(
                    "some error has occurred while trying to update foreignExchangeRate,, in class foreignExchangeRateService and its function updateforeignExchangeRate ",
                    e.getMessage());
            return new ResponseEntity<>("Foreign exchanges rate could not be updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * delete foreign exchanges
     *
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> deleteForeignExchangeRate(Long id) {
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
            log.error(
                    "some error has occurred while trying to Delete foreignExchangeRate,, in class foreignExchangeRateService and its function deleteforeignExchangeRate ",
                    e.getMessage(), e.getCause());
            return new ResponseEntity<>("ForeignExchangeRate could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
