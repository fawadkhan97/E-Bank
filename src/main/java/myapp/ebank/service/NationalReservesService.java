package myapp.ebank.service;

import myapp.ebank.model.entity.NationalReserves;
import myapp.ebank.repository.NationalReservesRepository;
import myapp.ebank.util.DateTime;
import myapp.ebank.util.ResponseMapping;
import myapp.ebank.util.SqlDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * The type National reserves service.
 */
@Service
public class NationalReservesService {

    private static final Logger log = LogManager.getLogger(NationalReservesService.class);
    /**
     * The National reserves repository.
     */
    NationalReservesRepository nationalReservesRepository;

    /**
     * Instantiates a new National reserves service.
     *
     * @param nationalReservesRepository the national reserves repository
     */
    public NationalReservesService(NationalReservesRepository nationalReservesRepository) {
        this.nationalReservesRepository = nationalReservesRepository;
    }

    /**
     * Gets daily national reserves.
     *
     * @return the daily national reserves
     */
    public ResponseEntity<Object> getDailyNationalReserves() throws ParseException {
        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            Optional<NationalReserves> nationalReserves = nationalReservesRepository.findByDateLike(currentDate);
            if (nationalReserves.isPresent()) {
                System.out.println("Reserves are  " + nationalReserves.get().getForeignReserves());
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.FOUND,"found data","",nationalReserves), HttpStatus.OK);
            } else
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "\"Could not get today rate...", "", null), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            System.out.println("some error has occurred " + e.getCause() + " " + e.getMessage());
            log.info(
                    "some error has occurred trying to Fetch national reserves, in Class nationalReservesService and its function get dailynational reserves ", e.getMessage());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "an error has occurred ", "", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Gets national reserves by date.
     *
     * @param date the date
     * @return the national reserves by date
     */
    public ResponseEntity<Object> getNationalReservesByDate(Date date) {

        try {
            Optional<NationalReserves> nationalReserves = nationalReservesRepository.findByDateLike(date);
            if (nationalReserves.isPresent()) {
                System.out.println("Reserves are " + nationalReserves.get().getGoldReserves() + " " + nationalReserves.get().getForeignReserves());
                return new ResponseEntity<>(nationalReserves, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today  ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occured " + e.getCause());
            log.info(
                    "some error has occurred trying to Fetch national reserves, in Class nationalReservesService and its function getnationalreserves by date ", e.getMessage());

            return new ResponseEntity<Object>("an error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Gets national reserves rate by start date.
     *
     * @param startDate the start date
     * @return the national reserves rate by start date
     */
    public ResponseEntity<Object> getNationalReservesRateByStartDate(@RequestParam java.util.Date startDate) {
        try {
            Optional<NationalReserves> interestRate = nationalReservesRepository.findByStartDate(startDate);
            if (interestRate.isPresent()) {
                //System.out.println("NationalReservesExchange  is " + NationalReservesExchangeRate.get().getCurrency() + " " + NationalReservesExchangeRate.get().getBuying());
                return new ResponseEntity<>(interestRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get NationalReserves  ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch national reserves, in Class nationalReservesService and its function getnationalreservesbystartingdate ", e.getMessage());
            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets national reserves rate between dates.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the national reserves rate between dates
     */
    public ResponseEntity<Object> getNationalReservesRateBetweenDates(@RequestParam java.util.Date startDate, @RequestParam java.util.Date endDate) {
        try {
            List<NationalReserves> interestRate = nationalReservesRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
            if (!interestRate.isEmpty()) {
                return new ResponseEntity<>(interestRate, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get NationalReserves  ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch national reserves, in Class nationalReservesService and its function getnationalreserves between dates ", e.getMessage());

            System.out.println("some error has occurred " + e.getCause());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * List all national reserves response entity.
     *
     * @return the response entity
     */
    public ResponseEntity<Object> listAllNationalReserves() {
        try {
            List<NationalReserves> nationalReserves = nationalReservesRepository.findAllByIsActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (nationalReserves.isEmpty()) {
                return new ResponseEntity<>("  NationalReserves are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(nationalReserves, HttpStatus.OK);
            }

        } catch (Exception e) {
            log.info("some error has occurred trying to Fetch nationalReserves, in Class  NationalReservesService and its function listAllNationalReserves ");
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("NationalReserves could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Gets national reserves by id.
     *
     * @param id the id
     * @return the national reserves by id
     */
    public ResponseEntity<Object> getNationalReservesById(Long id) {
        try {
            Optional<NationalReserves> nationalReserve = nationalReservesRepository.findById(id);
            if (nationalReserve.isPresent() && nationalReserve.get().isActive()) {
                // check if nationalReserve is verified
                log.info("nationalReserve fetch and found from db by id  : ", nationalReserve.toString());
                return new ResponseEntity<>(nationalReserve, HttpStatus.FOUND);
            } else {
                log.info("no nationalReserve found with id:", nationalReserve.get().getId());
                return new ResponseEntity<>("could not found nationalReserve with given details.... nationalReserve may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching NationalReserves by id , in class NationalReservesService and its function getNationalReservesById ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("Unable to find NationalReserves, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Add national reserves response entity.
     *
     * @param nationalReserves the national reserves
     * @return the response entity
     */
    public ResponseEntity<Object> addNationalReserves(NationalReserves nationalReserves) {
        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            nationalReserves.setUpdatedDate(currentDate);
            nationalReserves.setActive(true);
            nationalReservesRepository.save(nationalReserves);
            return new ResponseEntity<>(nationalReserves, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Fetch national reserves, in Class nationalReservesService and its function add nationalreserves ", e.getMessage());
            System.out.println("error occured .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occured  while trying to add National Reserves", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Update national reserves response entity.
     *
     * @param nationalReserves the national reserves
     * @return the response entity
     */
    public ResponseEntity<Object> updateNationalReserves(NationalReserves nationalReserves) {
        try {
            Date currentDate = SqlDate.getDateInSqlFormat();
            nationalReserves.setUpdatedDate(currentDate);
            nationalReservesRepository.save(nationalReserves);
            return new ResponseEntity<>(nationalReserves, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.info(
                    "some error has occurred while trying to update nationalReserve,, in class NationalReservesService and its function updateNationalReserves ",
                    e.getMessage());
            return new ResponseEntity<>("National Reserves could not be Updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete national reserves response entity.
     *
     * @param id the id
     * @return the response entity
     */
    public ResponseEntity<Object> deleteNationalReserves(Long id) {
        try {
            Optional<NationalReserves> nationalReserve = nationalReservesRepository.findByIdAndIsActive(id,true);
            if (nationalReserve.isPresent()) {
                // set status false
                nationalReserve.get().setActive(false);
                // set updated date
                java.util.Date date = DateTime.getDateTime();
                nationalReserve.get().setUpdatedDate(date);
                nationalReservesRepository.save(nationalReserve.get());
                return new ResponseEntity<>(" NationalReserves deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>(" NationalReserves does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
       log.info(
                    "some error has occurred while trying to Delete nationalReserve,, in class NationalReservesService and its function deleteNationalReserves ",
                    e.getMessage(), e.getCause());
            return new ResponseEntity<>("NationalReserves could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
