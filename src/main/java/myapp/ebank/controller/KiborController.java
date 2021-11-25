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

@RestController
@RequestMapping("/kibor")
@Validated
public class KiborController {
    final KiborService kiborRatesService;

    public KiborController(KiborService kiborRatesService) {
        this.kiborRatesService = kiborRatesService;
    }


    /**
     * fetch daily kibor rates
     *
     * @return daily kibor rates
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> dailyKiborRates(HttpServletRequest httpServletRequest) {
        return kiborRatesService.dailyKiborRates(httpServletRequest);
    }

    /**
     * get rates for specified date
     *
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getKiborRatesByDate(@RequestParam Date date, HttpServletRequest httpServletRequest) {
        return kiborRatesService.getKiborRateByDate(date, httpServletRequest);
    }

    /**
     * get kibor rates from start date to  current date
     *
     * @param startDate
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getKiborRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate,
                                                          HttpServletRequest httpServletRequest) {
        return kiborRatesService.getKiborRateByStartDate(startDate, httpServletRequest);
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
                                                                @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate,
                                                                HttpServletRequest httpServletRequest) {
        return kiborRatesService.getKiborRateBetweenDates(startDate, endDate, httpServletRequest);
    }

    /**
     * @param id
     * @return kiborRate object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getKiborRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return kiborRatesService.getKiborRatesById(id, httpServletRequest);
    }


    /**
     * @return list of Kibor rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllKiborRates(HttpServletRequest httpServletRequest) {
        return kiborRatesService.listAllKiborRates(httpServletRequest);
    }


    /**
     * save kibor rate
     *
     * @param kiborRate
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addKiborRate(
            @Valid @RequestBody KiborRates kiborRate,
            HttpServletRequest httpServletRequest) {

        return kiborRatesService.addKiborRate(kiborRate, httpServletRequest);

    }


    /**
     * @param kiborRate
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateKiborRate(@Valid @RequestBody KiborRates kiborRate, HttpServletRequest httpServletRequest) {

        return kiborRatesService.updateKiborRate(kiborRate, httpServletRequest);

    }

    /**
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteKiborRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        return kiborRatesService.deleteKiborRate(id, httpServletRequest);

    }


}
