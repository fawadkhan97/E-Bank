package myapp.ebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import myapp.ebank.model.entity.Currencies;
import myapp.ebank.service.CurrencyService;

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
                                               @Valid @RequestBody Currencies currency) {
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
                                               @Valid  @RequestBody Currencies currency) {
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


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
