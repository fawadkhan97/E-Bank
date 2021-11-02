package myapp.ebank.service;

import myapp.ebank.model.ForeignExchangeRates;
import myapp.ebank.repository.ForeignExchangeRateRepository;
import myapp.ebank.util.DateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ForeignExchangeRateService {


    ForeignExchangeRates foreignExchangeRate;
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

            Date date = DateTime.getDateTime();

            Optional<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findByDate(date);

            if (foreignExchangeRate.isPresent()) {
                System.out.println("foreignExchange rate is " + foreignExchangeRate.get().getCurrency() + " " + foreignExchangeRate.get().getBuying());
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occurred " + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * get foreignExchange Rate for specific Date
     *
     * @param date
     * @return
     */
    public ResponseEntity<Object> getForeignExchangeRateByDate(Date date) {

        try {

            Optional<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findByDate(date);

            if (foreignExchangeRate.isPresent()) {
                System.out.println("foreignExchange rate is " + foreignExchangeRate.get().getCurrency() + " " + foreignExchangeRate.get().getBuying());
                return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * save foreignExchange rate
     *
     * @param foreignExchangeRates
     * @return
     */
    public ResponseEntity<Object> addForeignExchangeRate(List <ForeignExchangeRates> foreignExchangeRates) {
        try {
            foreignExchangeRateRepository.saveAll(foreignExchangeRates);
            return new ResponseEntity<>(foreignExchangeRate, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " "+e.getMessage() );
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error occurred .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param foreignExchangeRates
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> updateForeignExchangeRate( List <ForeignExchangeRates> foreignExchangeRates) {
        try {
            foreignExchangeRateRepository.saveAll(foreignExchangeRates);
            return new ResponseEntity<>(foreignExchangeRates, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
		/*	log.error(
					"some error has occurred while trying to update foreignExchangeRate,, in class foreignExchangeRateService and its function updateforeignExchangeRate ",
					e.getMessage());*/
            return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> deleteForeignExchangeRate(Long id) {
        try {
            Optional<ForeignExchangeRates> foreignExchangeRate = foreignExchangeRateRepository.findById(id);
            if (foreignExchangeRate.isPresent()) {

                foreignExchangeRateRepository.deleteById(id);

                return new ResponseEntity<>("SMS: ForeignExchangeRate deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("SMS: ForeignExchangeRate does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
		/*	log.error(
					"some error has occurred while trying to Delete foreignExchangeRate,, in class foreignExchangeRateService and its function deleteforeignExchangeRate ",
					e.getMessage(), e.getCause(), e);*/
            return new ResponseEntity<>("ForeignExchangeRate could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
