package myapp.ebank.service;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.repository.FundRepository;
import myapp.ebank.util.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * The type Fund service.
 */
@Service
public class FundService {

    private static final Logger log = LogManager.getLogger(FundService.class);
    /**
     * The Fund repository.
     */
    final FundRepository fundRepository;

    /**
     * Instantiates a new Fund service.
     *
     * @param fundRepository the fund repository
     */
    public FundService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    /**
     * List all funds response entity.
     *
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> listAllFunds(HttpServletRequest httpServletRequest) {
        try {
            List<Funds> funds = fundRepository.findAll();
            // check if list is empty
            if (funds.isEmpty()) {
                return new ResponseEntity<>("  Funds are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(funds, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch funds, in Class  FundService and its function listAllFunds " + e.getMessage());
            return new ResponseEntity<>("Funds could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update fund response entity.
     *
     * @param fund               the fund
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> updateFund(Funds fund, HttpServletRequest httpServletRequest) {
        try {
            fund.setUpdatedDate(DateTime.getDateTime());
            fundRepository.save(fund);
            return new ResponseEntity<>(fund, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.info(
                    "some error has occurred while trying to update fund,, in class fundService and its function updatefund " + e.getMessage());
            return new ResponseEntity<>("Funds could not be updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete fund response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    public ResponseEntity<Object> deleteFund(Long id, HttpServletRequest httpServletRequest) {
        try {
            Optional<Funds> fund = fundRepository.findById(id);
            if (fund.isPresent()) {

                // set status false
                fund.get().setActive(false);
                // set updated date
                java.util.Date date = DateTime.getDateTime();
                fund.get().setUpdatedDate(date);
                fundRepository.save(fund.get());
                fundRepository.deleteById(id);

                return new ResponseEntity<>(" Funds deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>(" Funds does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred while trying to Delete fund,, in class fundService and its function deletefund " + e.getMessage() + e.getCause());
            return new ResponseEntity<>("Funds could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
