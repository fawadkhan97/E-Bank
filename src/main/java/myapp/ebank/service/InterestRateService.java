package myapp.ebank.service;

import myapp.ebank.model.entity.InterestRates;
import myapp.ebank.repository.InterestRatesRepository;
import myapp.ebank.util.SqlDate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Optional;

@Service
public class InterestRateService {

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
            // TODO: handle exception
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
            // TODO: handle exception
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
                //System.out.println("InterestExchange rate is " + InterestExchangeRate.get().getCurrency() + " " + InterestExchangeRate.get().getBuying());
                return new ResponseEntity<>(interestRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Interest rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // TODO: handle exception
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
            Optional<InterestRates> interestRate = interestRatesRepository.findByStartAndEndDate(startDate, endDate);
            if (interestRate.isPresent()) {
                //System.out.println("InterestExchange rate is " + InterestExchangeRate.get().getCurrency() + " " + InterestExchangeRate.get().getBuying());
                return new ResponseEntity<>(interestRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get Interest rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
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
            System.out.println("error occurred .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);

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
