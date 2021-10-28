package myapp.ebank.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import myapp.ebank.model.InterestRates;
import myapp.ebank.repository.InterestRatesRepository;
import myapp.ebank.util.DateAndTime;

@Service
public class InterestRatesService {

	InterestRates interestRates;
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

			System.out.println(DateAndTime.getDate());

			String date = DateAndTime.getDate();

			Optional<InterestRates> interestRates = interestRatesRepository.findByDate(date);

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

			Optional<InterestRates> interestRates = interestRatesRepository.findByDate(date);

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

	public ResponseEntity<Object> addInterestRate(InterestRates interestRates) {

		try {
			
			interestRatesRepository.save(interestRates);
			return new ResponseEntity<Object>(interestRates, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Data already exists .. duplicates not allowed ", HttpStatus.CONFLICT);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error occured .." + e.getCause() + "  " + e.getMessage());
			return new ResponseEntity<>("some error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
