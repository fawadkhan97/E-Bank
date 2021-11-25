package myapp.ebank.controller;

import myapp.ebank.model.entity.KiborRates;
import myapp.ebank.service.KiborService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;

/**
 * The type Kibor controller.
 */
@RestController
@RequestMapping("/kibor")
@Validated
public class KiborController {
    /**
     * The Kibor rates service.
     */
    final KiborService kiborRatesService;

    /**
     * Instantiates a new Kibor controller.
     *
     * @param kiborRatesService the kibor rates service
     */
    public KiborController(KiborService kiborRatesService) {
        this.kiborRatesService = kiborRatesService;
    }


    /**
     * Daily kibor rates response entity.
     *
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> dailyKiborRates(HttpServletRequest httpServletRequest) throws ParseException {
        return kiborRatesService.dailyKiborRates(httpServletRequest);
    }

    /**
     * Gets kibor rates by date.
     *
     * @param date               the date
     * @param httpServletRequest the http servlet request
     * @return the kibor rates by date
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getKiborRatesByDate(@RequestParam Date date, HttpServletRequest httpServletRequest) throws ParseException {
        return kiborRatesService.getKiborRateByDate(date, httpServletRequest);
    }

    /**
     * Gets kibor rate by start date.
     *
     * @param startDate          the start date
     * @param httpServletRequest the http servlet request
     * @return the kibor rate by start date
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getKiborRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate,
                                                          HttpServletRequest httpServletRequest) {
        return kiborRatesService.getKiborRateByStartDate(startDate, httpServletRequest);
    }

    /**
     * Gets kibor rate by start and end date.
     *
     * @param startDate          the start date
     * @param endDate            the end date
     * @param httpServletRequest the http servlet request
     * @return the kibor rate by start and end date
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getKiborRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate,
                                                                HttpServletRequest httpServletRequest) {
        return kiborRatesService.getKiborRateBetweenDates(startDate, endDate, httpServletRequest);
    }

    /**
     * Gets kibor rate.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the kibor rate
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getKiborRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return kiborRatesService.getKiborRatesById(id, httpServletRequest);
    }


    /**
     * Gets all kibor rates.
     *
     * @param httpServletRequest the http servlet request
     * @return the all kibor rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllKiborRates(HttpServletRequest httpServletRequest) throws ParseException {
        return kiborRatesService.listAllKiborRates(httpServletRequest);
    }


    /**
     * Add kibor rate response entity.
     *
     * @param kiborRate          the kibor rate
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addKiborRate(
            @Valid @RequestBody KiborRates kiborRate,
            HttpServletRequest httpServletRequest) {

        return kiborRatesService.addKiborRate(kiborRate, httpServletRequest);

    }


    /**
     * Update kibor rate response entity.
     *
     * @param kiborRate          the kibor rate
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateKiborRate(@Valid @RequestBody KiborRates kiborRate, HttpServletRequest httpServletRequest) {

        return kiborRatesService.updateKiborRate(kiborRate, httpServletRequest);

    }

    /**
     * Delete kibor rate response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteKiborRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        return kiborRatesService.deleteKiborRate(id, httpServletRequest);

    }


}
