package myapp.ebank.controller;

import myapp.ebank.model.entity.Loans;
import myapp.ebank.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private static final String defaultAuthValue = "12345";
    final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * @param authValue
     * @return list of loans
     * @Author "Fawad khan"
     * @Description "Display all loans from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllLoans(@RequestHeader(value = "Authorization") String authValue) {
        if (authorize(authValue)) {
            return loanService.listAllLoans();
        } else
            return new ResponseEntity<>(" Not authorize", HttpStatus.UNAUTHORIZED);
    }

     /**
     * @param authValue
     * @param loan
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateLoan(@RequestHeader(value = "Authorization") String authValue,
                                             @RequestBody Loans loan) {
        if (authorize(authValue)) {
            return loanService.updateLoan(loan);
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
    public ResponseEntity<Object> deleteLoan(@RequestHeader(value = "Authorization") String authValue,
                                             @PathVariable Long id) {

        if (authorize(authValue)) {
            return loanService.deleteLoan(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }
}
