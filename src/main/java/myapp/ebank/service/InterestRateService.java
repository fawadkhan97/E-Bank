package myapp.ebank.service;

import myapp.ebank.model.entity.InterestRates;
import myapp.ebank.repository.InterestRatesRepository;
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
public class InterestRateService {

    private static final Logger log = LogManager.getLogger(InterestRateService.class);
    InterestRatesRepository interestRatesRepository;

    public InterestRateService(InterestRatesRepository interestRatesRepository) {
        this.interestRatesRepository = interestRatesRepository;
    }

    /**
     * get daily interest rate
     *
     * @return InterestRate Object
     */
    public ResponseEntity<Object> getDailyInterestRate() {
        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            Optional<InterestRates> interestRates = interestRatesRepository.findByDateLike(currentDate);
            if (interestRates.isPresent()) {
                System.out.println("interest rate is " + interestRates.get().getInterestRate());
                return new ResponseEntity<>(interestRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug("some error has occurred trying to Fetch Interest rates, in Class InterestRateService and its function getDailyInterestRates ", e.getMessage());
            System.out.println("some error has occurred " + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * get interest Rate for specific Date
     *
     * @param date
     * @return
     */
    public ResponseEntity<Object> getInterestRateByDate(Date date) {
        try {
            Optional<InterestRates> interestRates = interestRatesRepository.findByDateLike(date);
            if (interestRates.isPresent()) {
                return new ResponseEntity<>(interestRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Interest rates, in Class InterestRateService and its function getInterestRatesByDate ", e.getMessage());
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
    public ResponseEntity<Object> getInterestRateByStartDate(@RequestParam java.util.Date startDate) {
        try {
            Optional<InterestRates> interestRate = interestRatesRepository.findByStartDate(startDate);
            if (interestRate.isPresent()) {
                return new ResponseEntity<>(interestRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Interest rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Interest rates, in Class InterestRateService and its function getRxchangeRatesByStartingDate ", e.getMessage());
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
    public ResponseEntity<Object> getInterestRateBetweenDates(@RequestParam java.util.Date startDate, @RequestParam java.util.Date endDate) {
        try {
            List<InterestRates> interestRates = interestRatesRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
            if (!interestRates.isEmpty()) {
                return new ResponseEntity<>(interestRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Interest rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Interest rates, in Class InterestRateService and its function getInterestRatesBetweenDates ", e.getMessage());
            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return List ofinterestRates
     * @author Fawad khan
     */
    public ResponseEntity<Object> listAllInterestRates() {
        try {
            List<InterestRates> interestRates = interestRatesRepository.findAllByActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (interestRates.isEmpty()) {
                return new ResponseEntity<>("  InterestRates are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(interestRates, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("some error has occurred trying to FetchinterestRates, in Class  InterestRatesService and its function listAllInterestRates ");
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("InterestRates could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * fetch record by id
     *
     * @param id
     * @return
     * @author fawad khan
     */
    public ResponseEntity<Object> getInterestRatesById(Long id) {
        try {
            Optional<InterestRates> interestRate = interestRatesRepository.findById(id);
            if (interestRate.isPresent() && interestRate.get().isActive()) {
                // check ifinterestRate is verified
                log.info("interestRate fetch and found from db by id  : ", interestRate.toString());
                return new ResponseEntity<>(interestRate, HttpStatus.FOUND);
            } else {
                log.info("nointerestRate found with id:", interestRate.get().getId());
                return new ResponseEntity<>("could not foundinterestRate with given details....interestRate may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.debug(
                    "some error has occurred during fetching InterestRates by id , in class InterestRatesService and its function getInterestRatesById ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("Unable to find InterestRates, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * save interest rate
     *
     * @param interestRates
     * @return
     */
    public ResponseEntity<Object> addInterestRate(InterestRates interestRates) {

        try {
            interestRatesRepository.save(interestRates);
            return new ResponseEntity<Object>(interestRates, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Interest rates, in Class InterestRateService and its function get dailyInterest rates ", e.getMessage());

            System.out.println("error occurred .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * update record
     *
     * @param interestRate
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> updateInterestRate(InterestRates interestRate) {
        try {
            interestRate.setUpdatedDate(DateTime.getDateTime());
            interestRatesRepository.save(interestRate);
            return new ResponseEntity<>(interestRate, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.debug(
                    "some error has occurred while trying to update interestRate,, in class interestRateService and its function updateinterestRate ",
                    e.getMessage());
            return new ResponseEntity<>("InterestRate could not be updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * delete record
     *
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> deleteInterestRate(Long id) {
        try {
            Optional<InterestRates> interestRate = interestRatesRepository.findById(id);
            if (interestRate.isPresent()) {
                // set status false
                interestRate.get().setActive(false);
                // set updated date
                java.util.Date date = DateTime.getDateTime();
                interestRate.get().setUpdatedDate(date);
                interestRatesRepository.save(interestRate.get());
                return new ResponseEntity<>("InterestRates deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("InterestRates does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred while trying to Delete interestRate,, in class interestRateService and its function deleteinterestRate ",
                    e.getMessage(), e.getCause());
            return new ResponseEntity<>("InterestRates could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
