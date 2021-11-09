package myapp.ebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import myapp.ebank.model.entity.Currencies;
import myapp.ebank.service.CurrencyService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private final String defaultAuthValue = "12345";
    CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * @param authValue
     * @return
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * get all currency notes
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }


    /**
     * @param authValue
     * @param currency
     * @return added currency object
     * @author Fawad khan
     * @createdDate 27-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addCurrencies(@RequestHeader(value = "Authorization") String authValue,
                                                @RequestBody Currencies currency) {
        // check authorization
        if (authorize(authValue)) {
            return currencyService.saveCurrency(currency);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param currency
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateCurrency(@RequestHeader(value = "Authorization") String authValue,
                                                 @RequestBody Currencies currency) {
        if (authorize(authValue)) {
            return currencyService.updateCurrency(currency);
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
    public ResponseEntity<Object> deleteCurrency(@RequestHeader(value = "Authorization") String authValue,
                                                 @PathVariable Long id) {
        if (authorize(authValue)) {
            return currencyService.deleteCurrency(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

}
