package myapp.ebank.controller;


import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.service.NationalReservesService;
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
@RequestMapping("/nationalReserves")
@Validated
public class NationalReserveController {

    private static final String defaultAuthValue = "12345";
    final NationalReservesService nationalReservesService;

    public NationalReserveController(NationalReservesService nationalReservesService) {
        this.nationalReservesService = nationalReservesService;
    }
    //private static final Logger log = LogManager.getLogger();

    /**
     * check nationalreserves is authorized or not
     *
     * @param authValue
     * @return
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * @param authValue
     * @return list of national reserves
     * @Author "Fawad khan"
     * @Description "Display all nationalreserves from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 30-oct-2021
     */
    @GetMapping("/today")
    public ResponseEntity<Object> getTodayNationalReserves(@RequestHeader(value = "Authorization", required = false) String authValue) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return nationalReservesService.getDailyNationalReserves();
            } else
                return new ResponseEntity<>(" Not authorize", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }

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
     * @param authValue
     * @param nationalreserves
     * @return added nationalreserves object
     * @author Fawad khan
     * @createdDate 30-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addNationalReserves(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                      @Valid @RequestBody NationalReserves nationalreserves) {
        // check authorization
        if (authValue != null) {
            if (authorize(authValue)) {
                return nationalReservesService.addNationalReserves(nationalreserves);
            } else
                return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * @param authValue
     * @param nationalreserves
     * @return
     * @createdDate 30-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateNationalReserves(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                         @Valid @RequestBody NationalReserves nationalreserves) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return nationalReservesService.updateNationalReserves(nationalreserves);
            } else
                return new ResponseEntity<>(":  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param id
     * @return
     * @createdDate 30-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteNationalReserves(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                         @PathVariable Long id) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return nationalReservesService.deleteNationalReserves(id);
            } else
                return new ResponseEntity<>(":  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

   }