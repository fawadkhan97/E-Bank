package myapp.ebank.service;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.repository.FundRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundService {

    private static final Logger log = LogManager.getLogger(FundService.class);
    final FundRepository fundRepository;

    public FundService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    /**
     * @return List of funds
     * @author Fawad khan
     */
    public ResponseEntity<Object> listAllFunds() {
        try {
            List<Funds> funds = fundRepository.findAll();
            // check if list is empty
            if (funds.isEmpty()) {
                return new ResponseEntity<>("  Funds are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(funds, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(
                    "some error has occurred trying to Fetch funds, in Class  FundService and its function listAllFunds ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Funds could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param fund
     * @return
     * @author fawad khan
     * @createdDate 01-nov-2021
     */
    public ResponseEntity<Object> updateFund(Funds fund) {
        try {

            fundRepository.save(fund);
            return new ResponseEntity<>(fund, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
			log.error(
					"some error has occurred while trying to update fund,, in class fundService and its function updatefund ",
					e.getMessage());
            return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 01-nov-2021
     */
    public ResponseEntity<Object> deleteFund(Long id) {
        try {
            Optional<Funds> fund = fundRepository.findById(id);
            if (fund.isPresent()) {

                fundRepository.deleteById(id);

                return new ResponseEntity<>("SMS: Funds deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("SMS: Funds does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
		/*	log.error(
					"some error has occurred while trying to Delete fund,, in class fundService and its function deletefund ",
					e.getMessage(), e.getCause(), e);*/
            return new ResponseEntity<>("Funds could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
