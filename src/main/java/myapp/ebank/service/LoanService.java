package myapp.ebank.service;

import myapp.ebank.model.entity.Loans;
import myapp.ebank.repository.LoanRepository;
import myapp.ebank.util.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {


    private static final Logger log = LogManager.getLogger(LoanService.class);
    final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    /**
     * @return List of loans
     * @author Fawad khan
     */
    public ResponseEntity<Object> listAllLoans() {
        try {
            List<Loans> loans = loanRepository.findAllByIsActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (loans.isEmpty()) {
                return new ResponseEntity<>("  Loans are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(loans, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch loans, in Class  LoanService and its function listAllLoan ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Loans could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param loan
     * @return
     * @author fawad khan
     * @createdDate 01-nov-2021
     */
    public ResponseEntity<Object> updateLoan(Loans loan) {
        try {
                Date updatedDate = DateTime.getDateTime();
                loan.setUpdatedDate(updatedDate);
                loanRepository.save(loan);
                return new ResponseEntity<>(loan, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.info(
                    "some error has occurred while trying to update loan,, in class loanService and its function updateloan ",
                    e.getMessage());
            return new ResponseEntity<>("Loans could not be updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 01-nov-2021
     */
    public ResponseEntity<Object> deleteLoan(Long id) {
        try {
            Optional<Loans> loan = loanRepository.findByIdAndIsActive(id, true);
            if (loan.isPresent()) {
                // set status false
                loan.get().setActive(false);
                // set updated date
                java.util.Date date = DateTime.getDateTime();
                loan.get().setUpdatedDate(date);
                loanRepository.save(loan.get());
                return new ResponseEntity<>("Loans deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("Loans does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred while trying to Delete loan, in class loanService and its function deleteloan ",
                    e.getMessage(), e.getCause());
            return new ResponseEntity<>("Loans could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
