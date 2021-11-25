package myapp.ebank.service;

import myapp.ebank.model.entity.Currencies;
import myapp.ebank.repository.CurrencyRepository;
import myapp.ebank.util.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Currency service.
 */
@Service
public class CurrencyService {

    private static final Logger log = LogManager.getLogger(CurrencyService.class);
    /**
     * The Feign police record service.
     */
    final FeignPoliceRecordService feignPoliceRecordService;
    private final CurrencyRepository currencyRepository;

    /**
     * Instantiates a new Currency service.
     *
     * @param currencyRepository       the currency repository
     * @param feignPoliceRecordService the feign police record service
     */
    public CurrencyService(CurrencyRepository currencyRepository, FeignPoliceRecordService feignPoliceRecordService) {
        this.currencyRepository = currencyRepository;
        this.feignPoliceRecordService = feignPoliceRecordService;
    }

    /**
     * Gets all currencies.
     *
     * @param httpServletRequest the http servlet request
     * @return the all currencies
     */
    public ResponseEntity<Object> getAllCurrencies(HttpServletRequest httpServletRequest) {
        try {
            //check police record from e-police using feign client
       /*     String currency = feignPoliceRecordService.getByid(1);
            System.out.println("currency is " + currency);*/
            List<Currencies> issuedCurrencies = currencyRepository.findAllByIsActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (issuedCurrencies.isEmpty()) {
                return new ResponseEntity<>("No data available ..... ", HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity<>(issuedCurrencies, HttpStatus.OK);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch issued currencies, in Class issuedCurrenciesService and its function get all currencies {}", e.getMessage());
            return new ResponseEntity<>("an error has occurred..", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets currency by id.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the currency by id
     */
    public ResponseEntity<Object> getCurrencyById(Long id, HttpServletRequest httpServletRequest) {
        try {
            Optional<Currencies> currency = currencyRepository.findById(id);
            if (currency.isPresent() && currency.get().getIsActive()) {
                // check if currency is verified
                log.info("currency fetch and found from db by id  : "+ currency.toString());
                return new ResponseEntity<>(currency, HttpStatus.FOUND);
            } else {
                log.info("no currency found with id .."+ currency.get().getId());
                return new ResponseEntity<>("could not found currency with given details.... currency may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching Currency by id , in class CurrencyService and its function getCurrencyById "+
                    e.getMessage());
            return new ResponseEntity<>("Unable to find Currency, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Save currency response entity.
     *
     * @param currency           the currency
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> saveCurrency(Currencies currency, HttpServletRequest httpServletRequest) {
        try {
            Date date = DateTime.getDateTime();
            currency.setCreatedDate(date);
            currency.setIsActive(true);
            currency.setUpdatedDate(null);
            // save currency to db
            currencyRepository.save(currency);
            return new ResponseEntity<>(currency, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(" Data already exists ..or.. Some Data field maybe missing , ", HttpStatus.CONFLICT);
        } catch (Exception e) {

            log.error("some error has occurred while trying to save currency,, in class CurrencyService and its function saveCurrency "
                    , e.getMessage());

            System.out.println("error is " + e.getMessage() + "  " + e.getCause());
            return new ResponseEntity<>("currency could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Update currency response entity.
     *
     * @param currency           the currency
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> updateCurrency(Currencies currency, HttpServletRequest httpServletRequest) {
        try {

            currency.setUpdatedDate(DateTime.getDateTime());
            currencyRepository.save(currency);
            currency.toString();
            return new ResponseEntity<>(currency, HttpStatus.OK);
        } catch (Exception e) {
            log.error(
                    "some error has occurred while trying to update currency,, in class CurrencyService and its function updateCurrency "
                    , e.getMessage());

            return new ResponseEntity<>("currency could not be updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete currency response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> deleteCurrency(Long id, HttpServletRequest httpServletRequest) {
        try {
            Optional<Currencies> currency = currencyRepository.findById(id);
            if (currency.isPresent()) {

                // set status false
                currency.get().setIsActive(false);
                // set updated date
                Date date = DateTime.getDateTime();
                currency.get().setUpdatedDate(date);
                currencyRepository.save(currency.get());
                return new ResponseEntity<>("  Currency deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("  Currency does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {

            log.error(
                    "some error has occurred while trying to Delete currency,, in class CurrencyService and its function deleteCurrency "
                    , e.getMessage(), e.getCause(), e);

            return new ResponseEntity<>("Currency could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
