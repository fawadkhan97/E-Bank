package myapp.ebank.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import myapp.ebank.model.Kibor;
import myapp.ebank.model.Kibor;
import myapp.ebank.repository.KiborRepository;
import myapp.ebank.util.DateAndTime;

@Service
public class KiborService {

	KiborRepository kiborRepository;

	public KiborService(KiborRepository kiborRepository) {
		// TODO Auto-generated constructor stub
		this.kiborRepository = kiborRepository;
	}

	public ResponseEntity<Object> dailyKiborRates() {

		try {

			String date = DateAndTime.getDate();

			Optional<Kibor> kiborRates = kiborRepository.findByDate(date);

			if (kiborRates.isPresent()) {
				System.out.println("interest rate is " + kiborRates.get().getBid());
				return new ResponseEntity<>(kiborRates, HttpStatus.OK);
			} else
				return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("error has occured " + e.getMessage() + " " + e.getCause());

			return new ResponseEntity<>("some error has occured ...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * get interest Rate for specific Date
	 * 
	 * @param date
	 * @return
	 */
	public ResponseEntity<Object> getKiborByDate(String date) {

		try {

			Optional<Kibor> Kibor = kiborRepository.findByDate(date);

			if (Kibor.isPresent()) {
				System.out.println("interest rate is " + Kibor.get().getBid());
				return new ResponseEntity<>(Kibor, HttpStatus.OK);
			} else
				return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("some error has occured " + e.getCause());
			return new ResponseEntity<Object>("an error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	public ResponseEntity<Object> addKibor(Kibor Kibor) {

		try {
			kiborRepository.save(Kibor);
			return new ResponseEntity<Object>(Kibor, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Data already exists .. duplicates not allowed ", HttpStatus.CONFLICT);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error occured .." + e.getCause() + "  " + e.getMessage());
			return new ResponseEntity<>("some error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	
	
}
