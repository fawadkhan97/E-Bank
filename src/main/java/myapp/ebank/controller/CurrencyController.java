package myapp.ebank.controller;

import myapp.ebank.model.entity.Currencies;
import myapp.ebank.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/currency")
@Validated
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
    public ResponseEntity<Object> getAllCurrencies(HttpServletRequest httpServletRequest) {
        return currencyService.getAllCurrencies();
    }


    /**
     * @param id
     * @return currency object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getCurrency(@PathVariable Long id,HttpServletRequest httpServletRequest) {
        return currencyService.getCurrencyById(id);
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
                                                @Valid @RequestBody Currencies currency,HttpServletRequest httpServletRequest) {
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
                                                 @Valid @RequestBody Currencies currency,HttpServletRequest httpServletRequest) {
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
                                                 @PathVariable Long id,HttpServletRequest httpServletRequest) {
        if (authorize(authValue)) {
            return currencyService.deleteCurrency(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }
}
