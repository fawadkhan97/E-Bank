package myapp.ebank.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import myapp.ebank.model.entity.InterestRates;
import myapp.ebank.service.InterestRateService;
import myapp.ebank.util.ExceptionHandling;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/interestRate")
public class InterestRateController {
    private static final String defaultAuthValue = "12345";
    InterestRateService interestRateService;

    public InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
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
     *
     * @return
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> getDailyInterestRate() {
        return interestRateService.getDailyInterestRate();
    }

    /**
     * get rates by specific date
     *
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getInterestRateByDate(@RequestParam Date date) {
        return interestRateService.getInterestRateByDate(date);
    }

    /**
     * get interest rates from start date to  current date
     *
     * @param startDate
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getInterestRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate) {
        return interestRateService.getInterestRateByStartDate(startDate);
    }

    /**
     * get interest rates from start date to  end date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getInterestRateByStartAndEndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date endDate) {
        return interestRateService.getInterestRateBetweenDates(startDate, endDate);
    }

    /**
     * save interest rate
     *
     * @param interestRates
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addInterestRate(@RequestHeader(value = "Authorization") String authValue, @RequestBody InterestRates interestRates) {
        if (authorize(authValue)) {
            return interestRateService.addInterestRate(interestRates);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param interestRate
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateInterestRate(@RequestHeader(value = "Authorization") String authValue,
                                                     @RequestBody InterestRates interestRate) {
        if (authorize(authValue)) {
            return interestRateService.updateInterestRate(interestRate);
        } else
            return new ResponseEntity<>("not authorize ", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteInterestRate(@RequestHeader(value = "Authorization") String authValue,
                                                     @PathVariable Long id) {

        if (authorize(authValue)) {
            return interestRateService.deleteInterestRate(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, InvalidFormatException.class, DataIntegrityViolationException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ExceptionHandling.handleMethodArgumentNotValid(ex);
    }
}
