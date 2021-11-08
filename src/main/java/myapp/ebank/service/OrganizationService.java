package myapp.ebank.service;

import myapp.ebank.controller.UserController;
import myapp.ebank.model.entity.Organizations;
import myapp.ebank.repository.OrganizationRepository;
import myapp.ebank.util.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class OrganizationService {
    final private OrganizationRepository organizationRepository;
    private static final Logger log = LogManager.getLogger(OrganizationService.class);

    // Autowiring through constructor
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * @return List of organizations
     * @author Fawad khan
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
            log.error(
                    "some error has occurred trying to Fetch organizations, in Class  OrganizationService and its function listAllOrganization ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("Organizations could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    // get organization by specific id
    public ResponseEntity<Object> getOrganizationById(Long id) {
        try {
            Optional<Organizations> organization = organizationRepository.findById(id);
            if (organization.isPresent()) {
//                log.info("organization fetch and found from db by id  : ", organization.toString());
                return new ResponseEntity<>(organization, HttpStatus.FOUND);
            } else {
//                log.info("no organization found with id:", organization.get().getId());
                return new ResponseEntity<>("could not found organization with given details....", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {

            log.error(
                    "some error has occurred during fetching Organizations by id , in class OrganizationService and its function getOrganizationById ",
                    e.getMessage());

            System.out.println("error is" + e.getCause() + " " + e.getMessage());
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
            Date date = DateTime.getDateTime();
            organization.setCreatedDate(date);
            organization.setIsActive(true);
            // save organization to db
            organizationRepository.save(organization);
            return new ResponseEntity<>(organization, HttpStatus.OK);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error(
                    "some error has occurred while trying to save organization,, in class OrganizationService and its function saveOrganization ",
                    e.getMessage());
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

            Date date = DateTime.getDateTime();
            organization.setUpdatedDate(date);
            organizationRepository.save(organization);
            return new ResponseEntity<>(organization, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.error(
                    "some error has occurred while trying to update organization,, in class OrganizationService and its function updateOrganization ",
                    e.getMessage());
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
                Date date = DateTime.getDateTime();
                organization.get().setUpdatedDate(date);
                organizationRepository.save(organization.get());
                return new ResponseEntity<>(": Organizations deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>(": Organizations does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           log.error(
                    "some error has occurred while trying to Delete organization,, in class OrganizationService and its function deleteOrganization ",
                    e.getMessage(), e.getCause(), e);
            return new ResponseEntity<>("Organizations could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
