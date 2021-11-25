package myapp.ebank.controller;

import myapp.ebank.model.entity.Loans;
import myapp.ebank.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Loan controller.
 */
@RestController
@RequestMapping("/loan")
@Validated
public class LoanController {

    /**
     * The Loan service.
     */
    final LoanService loanService;

    /**
     * Instantiates a new Loan controller.
     *
     * @param loanService the loan service
     */
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    /**
     * Gets all loans.
     *
     * @return the all loans
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllLoans() {

        return loanService.listAllLoans();

    }

    /**
     * Update loan response entity.
     *
     * @param loan the loan
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateLoan(@Valid @RequestBody Loans loan) {

        return loanService.updateLoan(loan);

    }

    /**
     * Delete loan response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteLoan(@PathVariable Long id) {

        return loanService.deleteLoan(id);

    }
}
