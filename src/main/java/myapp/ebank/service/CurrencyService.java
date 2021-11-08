package myapp.ebank.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import myapp.ebank.util.DateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import myapp.ebank.model.entity.Currencies;
import myapp.ebank.repository.CurrencyRepository;

@Service
public class CurrencyService {

    final FeignPoliceRecordService feignPoliceRecordService;
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository, FeignPoliceRecordService feignPoliceRecordService) {
        this.currencyRepository = currencyRepository;
        this.feignPoliceRecordService = feignPoliceRecordService;
    }

    /**
     * get all active currencies
     *
     * @return
     */
    public ResponseEntity<Object> getAllCurrencies() {
        try {

       /*     String currency = feignPoliceRecordService.getByid(1);
            System.out.println("currency is " + currency);
*/
            List<Currencies> issuedCurrencies = currencyRepository.findAllByisActive(true);
            // check if list is empty
            if (issuedCurrencies.isEmpty()) {
                return new ResponseEntity<>("No data available ..... ", HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity<>(issuedCurrencies, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error occurred is ..." + e.getCause() + "  " + e.getMessage());

            return new ResponseEntity<>("an error has occurred..", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param currency
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> saveCurrency(Currencies currency) {
        try {

            Date date = DateTime.getDateTime();
            currency.setIssuedDate(date);
            currency.setIsActive(true);
            // save currency to db
            currencyRepository.save(currency);
            return new ResponseEntity<>(currency, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(" Data already exists ..or.. Some Data field maybe missing , ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            /*
             * log.error(
             * "some error has occurred while trying to save currency,, in class CurrencyService and its function saveCurrency "
             * , e.getMessage());
             */
            System.out.println("error is " + e.getMessage() + "  " + e.getCause());
            return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param currency
     * @return
     * @author fawad khan
     * @createdDate 29-oct-2021
     */
    public ResponseEntity<Object> updateCurrency(Currencies currency) {
        try {

            currency.setUpdatedDate(DateTime.getDateTime());
            currencyRepository.save(currency);
            currency.toString();
            return new ResponseEntity<>(currency, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            /*
             * log.error(
             * "some error has occurred while trying to update currency,, in class CurrencyService and its function updateCurrency "
             * , e.getMessage());
             */
            return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> deleteCurrency(Long id) {
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
            /*
             * log.error(
             * "some error has occurred while trying to Delete currency,, in class CurrencyService and its function deleteCurrency "
             * , e.getMessage(), e.getCause(), e);
             */
            return new ResponseEntity<>("Currency could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
