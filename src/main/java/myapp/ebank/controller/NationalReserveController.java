package myapp.ebank.controller;


import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.service.NationalReservesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;

/**
 * The type National reserve controller.
 */
@RestController
@RequestMapping("/nationalReserves")
@Validated
public class NationalReserveController {

    /**
     * The National reserves service.
     */
    final NationalReservesService nationalReservesService;

    /**
     * Instantiates a new National reserve controller.
     *
     * @param nationalReservesService the national reserves service
     */
    public NationalReserveController(NationalReservesService nationalReservesService) {
        this.nationalReservesService = nationalReservesService;
    }


    /**
     * Gets today national reserves.
     *
     * @return the today national reserves
     */
    @GetMapping("/today")
    public ResponseEntity<Object> getTodayNationalReserves() throws ParseException {
        return nationalReservesService.getDailyNationalReserves();


    }

    /**
     * Gets national reserves by date.
     *
     * @param date the date
     * @return the national reserves by date
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getNationalReservesByDate(@RequestParam Date date) {
        return nationalReservesService.getNationalReservesByDate(date);
    }


    /**
     * Gets national reserves rate by start date.
     *
     * @param startDate the start date
     * @return the national reserves rate by start date
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getNationalReservesRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate) {
        return nationalReservesService.getNationalReservesRateByStartDate(startDate);
    }

    /**
     * Gets national reserves rate by start and end date.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the national reserves rate by start and end date
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getNationalReservesRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate) {
        return nationalReservesService.getNationalReservesRateBetweenDates(startDate, endDate);
    }

    /**
     * Gets national reserves.
     *
     * @param id the id
     * @return the national reserves
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getNationalReserves(@PathVariable Long id) {
        return nationalReservesService.getNationalReservesById(id);
    }


    /**
     * Gets all national reserves.
     *
     * @return the all national reserves
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllNationalReserves() {
        return nationalReservesService.listAllNationalReserves();
    }


    /**
     * Add national reserves response entity.
     *
     * @param nationalreserves the nationalreserves
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addNationalReserves(@Valid @RequestBody NationalReserves nationalreserves) {
        return nationalReservesService.addNationalReserves(nationalreserves);


    }

    /**
     * Update national reserves response entity.
     *
     * @param nationalreserves the nationalreserves
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateNationalReserves(@Valid @RequestBody NationalReserves nationalreserves) {
        return nationalReservesService.updateNationalReserves(nationalreserves);

    }

    /**
     * Delete national reserves response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteNationalReserves(@PathVariable Long id) {
        return nationalReservesService.deleteNationalReserves(id);

    }

}