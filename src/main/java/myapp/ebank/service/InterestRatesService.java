package myapp.ebank.service;

import myapp.ebank.model.entity.InterestRates;
import myapp.ebank.repository.InterestRatesRepository;
import myapp.ebank.util.DateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InterestRatesService {

    InterestRatesRepository interestRatesRepository;

    public InterestRatesService(InterestRatesRepository interestRatesRepository) {
        this.interestRatesRepository = interestRatesRepository;
    }

    /**
     * get daily interest rate
     *
     * @return InterestRate Object
     */
    public ResponseEntity<Object> getDailyInterestRate() {

        try {
            String date = DateTime.getDateInString();
            System.out.println("\n \nDate is \n" + date);
            Optional<InterestRates> interestRates = interestRatesRepository.findByDateLike(date);
            if (interestRates.isPresent()) {
                System.out.println("interest rate is " + interestRates.get().getInterestRate());
                return new ResponseEntity<>(interestRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occured " + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<Object>("an error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * get interest Rate for specific Date
     *
     * @param date
     * @return
     */
    public ResponseEntity<Object> getInterestRateByDate(String date) {

        try {
            Optional<InterestRates> interestRates = interestRatesRepository.findByDateLike(date);
            if (interestRates.isPresent()) {
                System.out.println("interest rate is " + interestRates.get().getInterestRate());
                return new ResponseEntity<>(interestRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occured " + e.getCause());
            return new ResponseEntity<Object>("an error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
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
            // TODO: handle exception
            System.out.println("error occured .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * @param interestRate
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> updateInterestRate(InterestRates interestRate) {
        try {
            interestRatesRepository.save(interestRate);
            return new ResponseEntity<>(interestRate, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
		/*	log.error(
					"some error has occurred while trying to update interestRate,, in class interestRateService and its function updateinterestRate ",
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
    public ResponseEntity<Object> deleteInterestRate(Long id) {
        try {
            Optional<InterestRates> interestRate = interestRatesRepository.findById(id);
            if (interestRate.isPresent()) {

                interestRatesRepository.deleteById(id);

                return new ResponseEntity<>("SMS: InterestRates deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("SMS: InterestRates does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
		/*	log.error(
					"some error has occurred while trying to Delete interestRate,, in class interestRateService and its function deleteinterestRate ",
					e.getMessage(), e.getCause(), e);*/
            return new ResponseEntity<>("InterestRates could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
