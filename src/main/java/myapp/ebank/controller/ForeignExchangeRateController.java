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

/**
 * The type Foreign exchange rate controller.
 */
@RestController
@RequestMapping("/foreignExchangeRates")
@Validated
public class ForeignExchangeRateController {
    /**
     * The Foreign exchange rate service.
     */
    final ForeignExchangeRateService foreignExchangeRateService;

    /**
     * Instantiates a new Foreign exchange rate controller.
     *
     * @param foreignExchangeRateService the foreign exchange rate service
     */
    public ForeignExchangeRateController(ForeignExchangeRateService foreignExchangeRateService) {
        this.foreignExchangeRateService = foreignExchangeRateService;
    }


    /**
     * Gets daily foreign exchange rate.
     *
     * @param httpServletRequest the http servlet request
     * @return the daily foreign exchange rate
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> getDailyForeignExchangeRate(HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getDailyForeignExchangeRate(httpServletRequest);
    }

    /**
     * Gets foreign exchange rate by date.
     *
     * @param date               the date
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rate by date
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getForeignExchangeRateByDate(@RequestParam Date date, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getForeignExchangeRateByDate(date, httpServletRequest);
    }


    /**
     * Gets foreign exchange rate by start date.
     *
     * @param startDate          the start date
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rate by start date
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getForeignExchangeRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate
            , HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getForeignExchangeRateByStartDate(startDate, httpServletRequest);
    }

    /**
     * Gets foreign exchange rate by start and end date.
     *
     * @param startDate          the start date
     * @param endDate            the end date
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rate by start and end date
     * @throws ParseException the parse exception
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getForeignExchangeRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                          @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate,
                                                                          HttpServletRequest httpServletRequest) throws ParseException {

        return foreignExchangeRateService.getForeignExchangeRateBetweenDates(startDate, endDate, httpServletRequest);
    }


    /**
     * Gets foreign exchange rate.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the foreign exchange rate
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getForeignExchangeRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.getForeignExchangeRatesById(id, httpServletRequest);
    }


    /**
     * Gets all foreign exchange rates.
     *
     * @param httpServletRequest the http servlet request
     * @return the all foreign exchange rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllForeignExchangeRates(HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.listAllForeignExchangeRates();
    }


    /**
     * Add foreign exchange rate response entity.
     *
     * @param foreignExchangeRates the foreign exchange rates
     * @param httpServletRequest   the http servlet request
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addForeignExchangeRate(@Valid @RequestBody List<ForeignExchangeRates> foreignExchangeRates, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.addForeignExchangeRate(foreignExchangeRates, httpServletRequest);
    }

    /**
     * Update foreign exchange rate response entity.
     *
     * @param foreignExchangeRates the foreign exchange rates
     * @param httpServletRequest   the http servlet request
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateForeignExchangeRate(@Valid @RequestBody List<ForeignExchangeRates> foreignExchangeRates, HttpServletRequest httpServletRequest) {
        return foreignExchangeRateService.updateForeignExchangeRate(foreignExchangeRates, httpServletRequest);
    }

    /**
     * Delete foreign exchange rate response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteForeignExchangeRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        return foreignExchangeRateService.deleteForeignExchangeRate(id, httpServletRequest);
    }


}
