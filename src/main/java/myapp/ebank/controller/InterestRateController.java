package myapp.ebank.controller;

import myapp.ebank.model.entity.InterestRates;
import myapp.ebank.service.InterestRateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;

/**
 * The type Interest rate controller.
 */
@RestController
@RequestMapping("/interestRate")
@Validated
public class InterestRateController {
    /**
     * The Interest rate service.
     */
    InterestRateService interestRateService;

    /**
     * Instantiates a new Interest rate controller.
     *
     * @param interestRateService the interest rate service
     */
    public InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    /**
     * Gets daily interest rate.
     *
     * @param httpServletRequest the http servlet request
     * @return the daily interest rate
     * @throws ParseException the parse exception
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> getDailyInterestRate(HttpServletRequest httpServletRequest) throws ParseException {
        return interestRateService.getDailyInterestRate(httpServletRequest);
    }

    /**
     * Gets interest rate by date.
     *
     * @param date               the date
     * @param httpServletRequest the http servlet request
     * @return the interest rate by date
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getInterestRateByDate(@RequestParam Date date, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRateByDate(date,httpServletRequest);
    }

    /**
     * Gets interest rate by start date.
     *
     * @param startDate          the start date
     * @param httpServletRequest the http servlet request
     * @return the interest rate by start date
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getInterestRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRateByStartDate(startDate,httpServletRequest);
    }

    /**
     * Gets interest rate by start and end date.
     *
     * @param startDate          the start date
     * @param endDate            the end date
     * @param httpServletRequest the http servlet request
     * @return the interest rate by start and end date
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getInterestRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                   @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRateBetweenDates(startDate, endDate,httpServletRequest);
    }

    /**
     * Gets interest rate.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the interest rate
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getInterestRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRatesById(id,httpServletRequest);
    }

    /**
     * Gets all interest rates.
     *
     * @param httpServletRequest the http servlet request
     * @return the all interest rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllInterestRates(HttpServletRequest httpServletRequest) {
        return interestRateService.listAllInterestRates(httpServletRequest);
    }

    /**
     * Add interest rate response entity.
     *
     * @param interestRates      the interest rates
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addInterestRate(            @Valid @RequestBody InterestRates interestRates, HttpServletRequest httpServletRequest) {
        return interestRateService.addInterestRate(interestRates,httpServletRequest);
    }

    /**
     * Update interest rate response entity.
     *
     * @param interestRate       the interest rate
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateInterestRate(            @Valid @RequestBody InterestRates interestRate, HttpServletRequest httpServletRequest) {
        return interestRateService.updateInterestRate(interestRate,httpServletRequest);
    }

    /**
     * Delete interest rate response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteInterestRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return interestRateService.deleteInterestRate(id,httpServletRequest);
    }


}
