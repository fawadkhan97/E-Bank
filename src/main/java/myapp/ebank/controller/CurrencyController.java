package myapp.ebank.controller;

import myapp.ebank.model.entity.Currencies;
import myapp.ebank.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/currency")
@Validated
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    /**
     * get all currency notes
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllCurrencies(HttpServletRequest httpServletRequest) {
        return currencyService.getAllCurrencies(httpServletRequest);
    }


    /**
     * @param id
     * @return currency object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getCurrency(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return currencyService.getCurrencyById(id, httpServletRequest);
    }


    /**
     * @param currency
     * @return added currency object
     * @author Fawad khan
     * @createdDate 27-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addCurrencies(@Valid @RequestBody Currencies currency, HttpServletRequest httpServletRequest) {

        return currencyService.saveCurrency(currency, httpServletRequest);
    }

    /**
     * @param currency
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateCurrency(@Valid @RequestBody Currencies currency, HttpServletRequest httpServletRequest) {

        return currencyService.updateCurrency(currency, httpServletRequest);

    }

    /**
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCurrency(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        return currencyService.deleteCurrency(id, httpServletRequest);
    }
}
