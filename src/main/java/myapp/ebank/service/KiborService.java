package myapp.ebank.service;

import java.util.Date;
import java.util.Optional;

import myapp.ebank.model.KiborRates;
import myapp.ebank.util.DateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import myapp.ebank.repository.KiborRepository;

@Service
public class KiborService {

    KiborRepository kiborRatesRepository;

    public KiborService(KiborRepository kiborRatesRepository) {
        // TODO Auto-generated constructor stub
        this.kiborRatesRepository = kiborRatesRepository;
    }

    /**
     * get daily kibor rates
     *
     * @return
     */
    public ResponseEntity<Object> dailyKiborRates() {
        try {
            Date date = DateTime.getDateTime();
            Optional<KiborRates> kiborRates = kiborRatesRepository.findByDate(date);
            if (kiborRates.isPresent()) {
                System.out.println("kibor rate is " + kiborRates.get().getBid());
                return new ResponseEntity<>(kiborRates, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rates  ...", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // TODO: handle exception

            System.out.println("error has occured " + e.getMessage() + " " + e.getCause());

            return new ResponseEntity<>("some error has occured ...", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * get kibor Rate for specific Date
     *
     * @param date
     * @return
     */
    public ResponseEntity<Object> getKiborRateByDate(Date date) {
        try {

            Optional<KiborRates> Kibor = kiborRatesRepository.findByDate(date);

            if (Kibor.isPresent()) {
                System.out.println("kibor rate is " + Kibor.get().getBid());
                return new ResponseEntity<>(Kibor, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not get today rate ...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("some error has occured " + e.getCause());
            return new ResponseEntity<Object>("an error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * save kibor rates
     *
     * @param KiborRates
     * @return
     */
    public ResponseEntity<Object> addKiborRate(KiborRates KiborRates) {

        try {
            kiborRatesRepository.save(KiborRates);
            return new ResponseEntity<Object>(KiborRates, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " "+e.getMessage() );
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error occured .." + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("some error has occured ", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    /**
     * @param kiborRate
     * @return
     * @author fawad khan
     * @createdDate 30-oct-2021
     */
    public ResponseEntity<Object> updateKiborRate(KiborRates kiborRate) {
        try {

            kiborRatesRepository.save(kiborRate);
            return new ResponseEntity<>(kiborRate, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
		/*	log.error(
					"some error has occurred while trying to update kiborRate,, in class kiborRateService and its function updatekiborRate ",
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
    public ResponseEntity<Object> deleteKiborRate(Long id) {
        try {
            Optional<KiborRates> kiborRate = kiborRatesRepository.findById(id);
            if (kiborRate.isPresent()) {

                kiborRatesRepository.deleteById(id);

                return new ResponseEntity<>("SMS: KiborRates deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("SMS: KiborRates does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
		/*	log.error(
					"some error has occurred while trying to Delete kiborRate,, in class kiborRateService and its function deletekiborRate ",
					e.getMessage(), e.getCause(), e);*/
            return new ResponseEntity<>("KiborRates could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
