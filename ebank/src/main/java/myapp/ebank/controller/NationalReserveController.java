package myapp.ebank.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import myapp.ebank.model.NationalReserves;
import myapp.ebank.service.NationalReservesService;

@RestController
@RequestMapping("/nationalReserves")
public class NationalReserveController {

    private static final String defaultAuthValue = "nationalreserves12345";
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
     * @param authValue
     * @param nationalreserves
     * @return added nationalreserves object
     * @author Fawad khan
     * @createdDate 30-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addNationalReserves(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                      @RequestBody NationalReserves nationalreserves) {
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
                                                         @RequestBody NationalReserves nationalreserves) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return nationalReservesService.updateNationalReserves(nationalreserves);
            } else
                return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
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
                return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

}