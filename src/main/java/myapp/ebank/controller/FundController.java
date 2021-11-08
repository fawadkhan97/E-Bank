package myapp.ebank.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import myapp.ebank.model.entity.Funds;
import myapp.ebank.service.FundService;
import myapp.ebank.util.ExceptionHandling;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fund")
public class FundController {

    private static final String defaultAuthValue = "12345";
    final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }


    /**
     * @param authValue
     * @return list of funds
     * @Author "Fawad khan"
     * @Description "Display all funds from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllFunds(@RequestHeader(value = "Authorization") String authValue) {
        if (authorize(authValue)) {
            return fundService.listAllFunds();
        } else
            return new ResponseEntity<>(" Not authorize", HttpStatus.UNAUTHORIZED);
    }


    /**
     * @param authValue
     * @param fund
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateFund(@RequestHeader(value = "Authorization") String authValue,
                                             @RequestBody Funds fund) {
        if (authorize(authValue)) {
            return fundService.updateFund(fund);
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
    public ResponseEntity<Object> deleteFund(@RequestHeader(value = "Authorization") String authValue,
                                             @PathVariable Long id) {

        if (authorize(authValue)) {
            return fundService.deleteFund(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, InvalidFormatException.class, DataIntegrityViolationException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ExceptionHandling.handleMethodArgumentNotValid(ex);
    }
}
