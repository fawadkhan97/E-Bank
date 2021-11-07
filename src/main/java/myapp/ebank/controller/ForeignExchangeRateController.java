package myapp.ebank.controller;

import myapp.ebank.model.entity.ForeignExchangeRates;
import myapp.ebank.service.ForeignExchangeRateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/foreignExchangeRates")
public class ForeignExchangeRateController {
    private static final String defaultAuthValue = "12345";
    final ForeignExchangeRateService foreignExchangeRateService;

    public ForeignExchangeRateController(ForeignExchangeRateService foreignExchangeRateService) {
        this.foreignExchangeRateService = foreignExchangeRateService;
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
    public ResponseEntity<Object> getDailyForeignExchangeRate() {
        return foreignExchangeRateService.getDailyForeignExchangeRate();
    }

    /**
     * get rates by specific date
     *
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getForeignExchangeRateByDate(@RequestParam Date date) {
        return foreignExchangeRateService.getForeignExchangeRateByDate(date);
    }


    /** get foreign rates from start date to  current date
     * @param startDate
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getForeignExchangeRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate) {
        return foreignExchangeRateService.getForeignExchangeRateByStartDate(startDate);
    }

    /**
     * get foreign rates from start date to  end date
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getForeignExchangeRateByStartAndEndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date endDate) {
        return foreignExchangeRateService.getForeignExchangeRateBetweenDates(startDate, endDate);
    }


    /**
     * save interest rate
     *
     * @param foreignExchangeRates
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addForeignExchangeRate(@RequestBody List<ForeignExchangeRates> foreignExchangeRates) {
        return foreignExchangeRateService.addForeignExchangeRate(foreignExchangeRates);
    }

    /**
     * @param authValue
     * @param foreignExchangeRates
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateForeignExchangeRate(@RequestHeader(value = "Authorization") String authValue,
                                                            @RequestBody List<ForeignExchangeRates> foreignExchangeRates) {
        if (authorize(authValue)) {
            return foreignExchangeRateService.updateForeignExchangeRate(foreignExchangeRates);
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
    public ResponseEntity<Object> deleteForeignExchangeRate(@RequestHeader(value = "Authorization") String authValue,
                                                            @PathVariable Long id) {

        if (authorize(authValue)) {
            return foreignExchangeRateService.deleteForeignExchangeRate(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }


}
