package myapp.ebank.controller;

import myapp.ebank.model.entity.Currencies;
import myapp.ebank.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * The type Currency controller.
 */
@RestController
@RequestMapping("/currency")
@Validated
public class CurrencyController {
    private final CurrencyService currencyService;

    /**
     * Instantiates a new Currency controller.
     *
     * @param currencyService the currency service
     */
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    /**
     * Gets all currencies.
     *
     * @param httpServletRequest the http servlet request
     * @return the all currencies
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllCurrencies(HttpServletRequest httpServletRequest) {
        return currencyService.getAllCurrencies(httpServletRequest);
    }


    /**
     * Gets currency.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the currency
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getCurrency(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return currencyService.getCurrencyById(id, httpServletRequest);
    }


    /**
     * Add currencies response entity.
     *
     * @param currency           the currency
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addCurrencies(@Valid @RequestBody Currencies currency, HttpServletRequest httpServletRequest) {

        return currencyService.saveCurrency(currency, httpServletRequest);
    }

    /**
     * Update currency response entity.
     *
     * @param currency           the currency
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateCurrency(@Valid @RequestBody Currencies currency, HttpServletRequest httpServletRequest) {

        return currencyService.updateCurrency(currency, httpServletRequest);

    }

    /**
     * Delete currency response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCurrency(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        return currencyService.deleteCurrency(id, httpServletRequest);
    }
}
