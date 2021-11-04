package myapp.ebank.controller;

import myapp.ebank.model.entity.KiborRates;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import myapp.ebank.service.KiborService;

import java.util.Date;

@RestController
@RequestMapping("/kibor")
public class KiborController {
    private static final String defaultAuthValue = "12345";
    final KiborService kiborRatesService;

    public KiborController(KiborService kiborRatesService) {
        this.kiborRatesService = kiborRatesService;
    }

    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    @GetMapping("/dailyRates")
    public ResponseEntity<Object> dailyKiborRates() {
        return kiborRatesService.dailyKiborRates();
    }


    @GetMapping("/getByDate")
    public ResponseEntity<Object> getKiborRatesByDate(@RequestParam Date date) {
        System.out.println(date);
        return kiborRatesService.getKiborRateByDate(date);
    }

    /**
     * save kibor rate
     * @param kiborRate
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addKiborRate(@RequestBody KiborRates kiborRate) {
        return kiborRatesService.addKiborRate(kiborRate);
    }

    /**
     * @param authValue
     * @param kiborRate
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateKiborRate(@RequestHeader(value = "Authorization") String authValue,
                                                  @RequestBody KiborRates kiborRate) {
        if (authorize(authValue)) {
            return kiborRatesService.updateKiborRate(kiborRate);
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
    public ResponseEntity<Object> deleteKiborRate(@RequestHeader(value = "Authorization") String authValue,
                                                  @PathVariable Long id) {

        if (authorize(authValue)) {
            return kiborRatesService.deleteKiborRate(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }


}
