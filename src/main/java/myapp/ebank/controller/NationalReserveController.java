package myapp.ebank.controller;


import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.service.NationalReservesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@RestController
@RequestMapping("/nationalReserves")
@Validated
public class NationalReserveController {

    final NationalReservesService nationalReservesService;

    public NationalReserveController(NationalReservesService nationalReservesService) {
        this.nationalReservesService = nationalReservesService;
    }


    /**
     * @return list of national reserves
     * @Author "Fawad khan"
     * @Description "Display all nationalreserves from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 30-oct-2021
     */
    @GetMapping("/today")
    public ResponseEntity<Object> getTodayNationalReserves() {
        return nationalReservesService.getDailyNationalReserves();


    }

    /**
     * get rates by specific date
     *
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getNationalReservesByDate(@RequestParam Date date) {
        return nationalReservesService.getNationalReservesByDate(date);
    }


    /**
     * get nationalReserves rates from start date to  current date
     *
     * @param startDate
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getNationalReservesRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate) {
        return nationalReservesService.getNationalReservesRateByStartDate(startDate);
    }

    /**
     * get nationalReserves rates from start date to  end date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getNationalReservesRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate) {
        return nationalReservesService.getNationalReservesRateBetweenDates(startDate, endDate);
    }

    /**
     * @param id
     * @return nationalReserves object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getNationalReserves(@PathVariable Long id) {
        return nationalReservesService.getNationalReservesById(id);
    }


    /**
     * @return list of national reservess
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllNationalReserves() {
        return nationalReservesService.listAllNationalReserves();
    }


    /**
     * add reserves record to db
     *
     * @param nationalreserves
     * @return added nationalreserves object
     * @author Fawad khan
     * @createdDate 30-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addNationalReserves(@Valid @RequestBody NationalReserves nationalreserves) {
        return nationalReservesService.addNationalReserves(nationalreserves);


    }

    /**
     * @param nationalreserves
     * @return
     * @createdDate 30-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateNationalReserves(@Valid @RequestBody NationalReserves nationalreserves) {
        return nationalReservesService.updateNationalReserves(nationalreserves);

    }

    /**
     * @param id
     * @return
     * @createdDate 30-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteNationalReserves(@PathVariable Long id) {
        return nationalReservesService.deleteNationalReserves(id);

    }

}