package myapp.ebank.controller;

import myapp.ebank.model.entity.Loans;
import myapp.ebank.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/loan")
@Validated
public class LoanController {

    final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    /**
     * @return list of loans
     * @Author "Fawad khan"
     * @Description "Display all loans from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllLoans() {

        return loanService.listAllLoans();

    }

    /**
     * @param loan
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateLoan(@Valid @RequestBody Loans loan) {

        return loanService.updateLoan(loan);

    }

    /**
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteLoan(@PathVariable Long id) {

        return loanService.deleteLoan(id);

    }
}
