package myapp.ebank.controller;

import myapp.ebank.model.entity.KiborRates;
import myapp.ebank.service.KiborService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kibor")
@Validated
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

    /**
     * get rates for specified date
     *
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
    public ResponseEntity<Object> getKiborRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate) {
        return kiborRatesService.getKiborRateBetweenDates(startDate, endDate);
    }

    /**
     * @param id
     * @return kiborRate object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getKiborRate(@PathVariable Long id) {
        return kiborRatesService.getKiborRatesById(id);
    }


    /**
     * @return list of Kibor rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllKiborRates() {
        return kiborRatesService.listAllKiborRates();
    }


    /**
     * save kibor rate
     *
     * @param kiborRate
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addKiborRate(@RequestHeader(value = "Authorization") String authValue,
                                               @Valid @RequestBody KiborRates kiborRate) {
        if (authorize(authValue)) {
            return kiborRatesService.addKiborRate(kiborRate);
        } else
            return new ResponseEntity<>("not authorize ", HttpStatus.UNAUTHORIZED);
    }


    /**
     * @param authValue
     * @param kiborRate
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateKiborRate(@RequestHeader(value = "Authorization") String authValue,
                                                  @Valid @RequestBody KiborRates kiborRate) {
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



}
