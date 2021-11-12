package myapp.ebank.controller;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.service.FundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/fund")
@Validated
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
    public ResponseEntity<Object> getAllFunds(@RequestHeader(value = "Authorization") String authValue, HttpServletRequest httpServletRequest) {
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
                                           @Valid @RequestBody Funds fund,HttpServletRequest httpServletRequest) {
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
                                             @PathVariable Long id,HttpServletRequest httpServletRequest) {

        if (authorize(authValue)) {
            return fundService.deleteFund(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }


}
