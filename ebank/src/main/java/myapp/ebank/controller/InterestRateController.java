package myapp.ebank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myapp.ebank.model.InterestRates;
import myapp.ebank.service.InterestRatesService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/interestRate")
public class InterestRateController {

	InterestRatesService interestRatesService;

	public InterestRateController(InterestRatesService interestRatesService) {
		this.interestRatesService = interestRatesService;
	}

	@GetMapping("/dailyRates")
	public ResponseEntity<Object> getDailyInterestRate() {

		return interestRatesService.getDailyInterestRate();

	}

	@GetMapping("/getByDate")
	public ResponseEntity<Object> getInterestRateByDate(@RequestParam String date) {
		System.out.println(date);
		return interestRatesService.getInterestRateByDate(date);
	}

//	@GetMapping("/getByDateRange")

	@PostMapping("/add")
	public ResponseEntity<Object> addInterestRate(@RequestBody InterestRates interestRates) {

		return interestRatesService.addInterestRate(interestRates);
	}

}
