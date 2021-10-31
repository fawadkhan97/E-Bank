package myapp.ebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import myapp.ebank.model.InterestRates;
import myapp.ebank.service.InterestRatesService;

@RestController
@RequestMapping("/interestRate")
public class InterestRateController {
	private static final String defaultAuthValue = "12345";

	InterestRatesService interestRatesService;

	public InterestRateController(InterestRatesService interestRatesService) {
		this.interestRatesService = interestRatesService;
	}


	/**
	 * check user is authorized or not
	 *
	 * @param authValue
	 * @return
	 */
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	/**
	 * return daily rates
	 * @return
	 */
	@GetMapping("/dailyRates")
	public ResponseEntity<Object> getDailyInterestRate() {
		return interestRatesService.getDailyInterestRate();
	}

	/**
	 * get rates by specific date
	 * @param date
	 * @return
	 */
	@GetMapping("/getByDate")
	public ResponseEntity<Object> getInterestRateByDate(@RequestParam String date) {
		System.out.println(date);
		return interestRatesService.getInterestRateByDate(date);
	}

	/**
	 * save interest rate
	 * @param interestRates
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<Object> addInterestRate(@RequestBody InterestRates interestRates) {
		return interestRatesService.addInterestRate(interestRates);
	}


	/**
	 *
	 * @param authValue
	 *
	 * @param interestRate
	 *
	 * @createdDate 29-oct-2021
	 *
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<Object> updateInterestRate(@RequestHeader(value = "Authorization") String authValue,
												 @RequestBody InterestRates interestRate) {
		if (authorize(authValue)) {
			return interestRatesService.updateInterestRate(interestRate);
		} else
			return new ResponseEntity<>("not authorize ", HttpStatus.UNAUTHORIZED);
	}

	/**
	 *
	 * @param authValue
	 * @param id
	 * @createdDate 27-oct-2021
	 * @return
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteInterestRate(@RequestHeader(value = "Authorization") String authValue,
												 @PathVariable Long id) {

		if (authorize(authValue)) {
			return interestRatesService.deleteInterestRate(id);
		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

}
