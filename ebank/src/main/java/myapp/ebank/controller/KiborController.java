package myapp.ebank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.http.Response;

import myapp.ebank.service.KiborService;

@RestController
@RequestMapping("/kibor")
public class KiborController {
	KiborService kiborService;

	@GetMapping("/dailyRates")
	public ResponseEntity<Object> dailyKiborRates() {
		return kiborService.dailyKiborRates();
	}
	
	
	@GetMapping("/getByDate")
	public ResponseEntity<Object> getKiborByDate(@RequestParam String date){
		System.out.println(date);
		return kiborService.getKiborByDate(date);
	}

}
