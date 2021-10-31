package myapp.ebank.service;

import myapp.ebank.model.NationalReserves;
import myapp.ebank.repository.NationalReservesRepository;
import myapp.ebank.util.DateAndTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;
@Service
public class NationalReservesService {

    NationalReservesRepository nationalReservesRepository;

    public NationalReservesService(NationalReservesRepository nationalReservesRepository) {
        this.nationalReservesRepository = nationalReservesRepository;
    }

    /**
     * get daily national Reserves
     *
     * @return NationalReserves Object
     */
    public ResponseEntity<Object> getDailyNationalReserves() {
        try {
            System.out.println(DateAndTime.getDate());
            String date = DateAndTime.getDate();
            Optional<NationalReserves> nationalReserves = nationalReservesRepository.findByDate(date);
            if (nationalReserves.isPresent()) {
                System.out.println("Reserves are  " + nationalReserves.get().getForeignReserves());
                return new ResponseEntity<>(nationalReserves, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occurred " + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<Object>("an error has occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * get national Reserves for specific Date
     *
     * @param date
     * @return
     */
    public ResponseEntity<Object> getNationalReservesByDate(String date) {

        try {
            Optional<NationalReserves> nationalReserves = nationalReservesRepository.findByDate(date);
            if (nationalReserves.isPresent()) {
                System.out.println("Reserves are " + nationalReserves.get().getGoldReserves() + " " + nationalReserves.get().getForeignReserves());
                return new ResponseEntity<>(nationalReserves, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occured " + e.getCause());
            return new ResponseEntity<Object>("an error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * save  National Reserves
     *
     * @param nationalReserves
     * @return
     */
    public ResponseEntity<Object> addNationalReserves(NationalReserves nationalReserves) {
        try {
            nationalReservesRepository.save(nationalReserves);
            return new ResponseEntity<Object>(nationalReserves, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Data already exists .. duplicates not allowed ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error occured .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param nationalReserve
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> updateNationalReserves(NationalReserves nationalReserve) {
        try {

            nationalReservesRepository.save(nationalReserve);
            nationalReserve.toString();
            return new ResponseEntity<>(nationalReserve, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
          /*  log.error(
                    "some error has occurred while trying to update nationalReserve,, in class NationalReservesService and its function updateNationalReserves ",
                    e.getMessage());*/
            return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> deleteNationalReserves(Long id) {
        try {
            Optional<NationalReserves> nationalReserve = nationalReservesRepository.findById(id);
            if (nationalReserve.isPresent()) {
                nationalReservesRepository.save(nationalReserve.get());
                return new ResponseEntity<>("SMS: NationalReserves deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("SMS: NationalReserves does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
       /*     log.error(
                    "some error has occurred while trying to Delete nationalReserve,, in class NationalReservesService and its function deleteNationalReserves ",
                    e.getMessage(), e.getCause(), e);*/
            return new ResponseEntity<>("NationalReserves could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
