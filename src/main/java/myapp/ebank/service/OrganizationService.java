package myapp.ebank.service;

import myapp.ebank.model.Organizations;
import myapp.ebank.repository.OrganizationRepository;
import myapp.ebank.util.DateAndTime;
import myapp.ebank.util.EmailUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {
    final private OrganizationRepository organizationRepository;

    // Autowiring through constructor
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * @author Fawad khan
     * @return List of organizations
     */
    // Get list of all organizations
    public ResponseEntity<Object> listAllOrganization() {
        try {

            List<Organizations> organizations = organizationRepository.findAllByisActive(true);
           /* log.info("list of  organizations fetch from db are ", organizations);*/
            // check if list is empty
            if (organizations.isEmpty()) {
                return new ResponseEntity<>("  Organizations are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(organizations, HttpStatus.OK);
            }
        } catch (Exception e) {
          /*  log.error(
                    "some error has occurred trying to Fetch organizations, in Class  OrganizationService and its function listAllOrganization ",
                    e.getMessage());*/
            System.out.println("error is"+ e.getCause() + " "+e.getMessage() );

            return new ResponseEntity<>("Organizations could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @author fawad khan
     * @createdDate 27-oct-2021
     * @param id
     * @return
     */
    // get organization by specific id
    public ResponseEntity<Object> getOrganizationById(Long id) {
        try {
            Optional<Organizations> organization = organizationRepository.findById(id);
            if (organization.isPresent()) {
//                log.info("organization fetch and found from db by id  : ", organization.toString());
                return new ResponseEntity<>(organization, HttpStatus.FOUND);
            } else
//                log.info("no organization found with id:", organization.get().getId());
            return new ResponseEntity<>("could not found organization with given details....", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
/*
            log.error(
                    "some error has occurred during fetching Organizations by id , in class OrganizationService and its function getOrganizationById ",
                    e.getMessage());
*/

            System.out.println("error is"+ e.getCause() + " "+e.getMessage() );
            return new ResponseEntity<>("Unable to find Organizations, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * @param organization
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> saveOrganization(Organizations organization) {
        try {
            String date = DateAndTime.getDate();
            organization.setCreatedDate(date);
            organization.setIsActive(true);
            // save organization to db
            organizationRepository.save(organization);
            organization.toString();
            return new ResponseEntity<>(organization, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
        /*    log.error(
                    "some error has occurred while trying to save organization,, in class OrganizationService and its function saveOrganization ",
                    e.getMessage());*/
            System.out.println("error is " + e.getMessage() + "  " + e.getCause());
            return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param organization
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> updateOrganization(Organizations organization) {
        try {
            String pattern = "dd-MM-yyyy hh:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            organization.setUpdatedDate(date);
            organizationRepository.save(organization);
            organization.toString();
            return new ResponseEntity<>(organization, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            /*log.error(
                    "some error has occurred while trying to update organization,, in class OrganizationService and its function updateOrganization ",
                    e.getMessage());*/
            return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> deleteOrganization(Long id) {
        try {
            Optional<Organizations> organization = organizationRepository.findById(id);
            if (organization.isPresent()) {

                // set status false
                organization.get().setIsActive(false);
                // set updated date
                String pattern = "dd-MM-yyyy hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());
                organization.get().setUpdatedDate(date);
                organizationRepository.save(organization.get());
                return new ResponseEntity<>("SMS: Organizations deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("SMS: Organizations does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           /* log.error(
                    "some error has occurred while trying to Delete organization,, in class OrganizationService and its function deleteOrganization ",
                    e.getMessage(), e.getCause(), e);*/
            return new ResponseEntity<>("Organizations could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
