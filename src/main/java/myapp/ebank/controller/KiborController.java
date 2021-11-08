package myapp.ebank.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import myapp.ebank.model.entity.KiborRates;
import myapp.ebank.service.KiborService;
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
@RequestMapping("/kibor")
public class KiborController {
    private static final String defaultAuthValue = "12345";
    final KiborService kiborRatesService;

    public KiborController(KiborService kiborRatesService) {
        this.kiborRatesService = kiborRatesService;
    }

    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * fetch daily kibor rates
     *
     * @return daily kibor rates
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> dailyKiborRates() {
        return kiborRatesService.dailyKiborRates();
    }

    /** get rates for specified date
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getKiborRatesByDate(@RequestParam Date date) {
        System.out.println(date);
        return kiborRatesService.getKiborRateByDate(date);
    }

    /**
     * get kibor rates from start date to  current date
     *
     * @param startDate
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getKiborRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate) {
        return kiborRatesService.getKiborRateByStartDate(startDate);
    }

    /**
     * get kibor rates from start date to  end date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getKiborRateByStartAndEndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date endDate) {
        return kiborRatesService.getKiborRateBetweenDates(startDate, endDate);
    }

    /**
     * save kibor rate
     *
     * @param kiborRate
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addKiborRate(@RequestBody KiborRates kiborRate) {
        return kiborRatesService.addKiborRate(kiborRate);
    }

    /**
     * @param authValue
     * @param kiborRate
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateKiborRate(@RequestHeader(value = "Authorization") String authValue,
                                                  @RequestBody KiborRates kiborRate) {
        if (authorize(authValue)) {
            return kiborRatesService.updateKiborRate(kiborRate);
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
    public ResponseEntity<Object> deleteKiborRate(@RequestHeader(value = "Authorization") String authValue,
                                                  @PathVariable Long id) {

        if (authorize(authValue)) {
            return kiborRatesService.deleteKiborRate(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, InvalidFormatException.class, DataIntegrityViolationException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ExceptionHandling.handleMethodArgumentNotValid(ex);
    }

}
