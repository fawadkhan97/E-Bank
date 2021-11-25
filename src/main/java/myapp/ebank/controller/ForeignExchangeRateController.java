package myapp.ebank.controller;

import myapp.ebank.model.entity.ForeignExchangeRates;
import myapp.ebank.service.ForeignExchangeRateService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/foreignExchangeRates")
@Validated
public class ForeignExchangeRateController {
    final ForeignExchangeRateService foreignExchangeRateService;

    public ForeignExchangeRateController(ForeignExchangeRateService foreignExchangeRateService) {
        this.foreignExchangeRateService = foreignExchangeRateService;
    }


    /**
     * return daily rates
     *
     * @return
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> getDailyForeignExchangeRate(HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getDailyForeignExchangeRate(httpServletRequest);
    }

    /**
     * get rates by specific date
     *
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getForeignExchangeRateByDate(@RequestParam Date date, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getForeignExchangeRateByDate(date, httpServletRequest);
    }


    /**
     * get foreign rates from start date to  current date
     *
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getForeignExchangeRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate
            , HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getForeignExchangeRateByStartDate(startDate, httpServletRequest);
    }

    /**
     * get foreign rates from start date to  end date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getForeignExchangeRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                          @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate,
                                                                          HttpServletRequest httpServletRequest) throws ParseException {

        return foreignExchangeRateService.getForeignExchangeRateBetweenDates(startDate, endDate, httpServletRequest);
    }


    /**
     * @param id
     * @return foreignExchangeRate object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getForeignExchangeRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getForeignExchangeRatesById(id, httpServletRequest);
    }


    /**
     * @return list of foreign exchange rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllForeignExchangeRates(HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.listAllForeignExchangeRates();
    }


    /**
     * save foreignExchangeRate
     *
     * @param foreignExchangeRates
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addForeignExchangeRate(@Valid @RequestBody List<ForeignExchangeRates> foreignExchangeRates, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.addForeignExchangeRate(foreignExchangeRates, httpServletRequest);
    }

    /**
     * @param foreignExchangeRates
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateForeignExchangeRate(@Valid @RequestBody List<ForeignExchangeRates> foreignExchangeRates, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.updateForeignExchangeRate(foreignExchangeRates, httpServletRequest);
    }

    /**
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteForeignExchangeRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        return foreignExchangeRateService.deleteForeignExchangeRate(id, httpServletRequest);
    }


}
