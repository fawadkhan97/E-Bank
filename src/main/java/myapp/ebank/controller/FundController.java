package myapp.ebank.controller;

import myapp.ebank.model.Funds;
import myapp.ebank.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funds")
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
     * add fund
     * @param fund
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addFund(@RequestBody Funds fund) {
        return fundService.addFund(fund);
    }


    /**
     *
     * @param authValue
     *
     * @param fund
     *
     * @createdDate 29-oct-2021
     *
     * @return
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
     *
     * @param authValue
     * @param id
     * @createdDate 27-oct-2021
     * @return
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteFund(@RequestHeader(value = "Authorization") String authValue,
                                                     @PathVariable Long id) {

        if (authorize(authValue)) {
            return fundService.deleteFund(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

}
