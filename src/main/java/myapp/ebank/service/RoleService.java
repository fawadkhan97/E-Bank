package myapp.ebank.service;

import myapp.ebank.model.entity.Roles;
import myapp.ebank.repository.RoleRepository;
import myapp.ebank.util.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author fawad khan
 * @createdDate 12-oct-2021
 */
@Service
public class RoleService {
    private static final Logger log = LogManager.getLogger(RoleService.class);

    final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * get list of all role available
     *
     * @return Role object if any role
     * @author Fawad khan
     * @Created Date 31-0ct-2021
     */
    public ResponseEntity<Object> getAllRoles() {
        try {
            List<Roles> roles = roleRepository.findAllByisActive(true);
            // check if list is empty or not
            if (roles.isEmpty()) {
                return new ResponseEntity<>("No roles found... ", HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(
                    "some error has occurred trying to Fetch Roles, in Class  RoleService and its function getALLRoles ",
                    e.getCause(), e.getMessage());
            System.out.println(e.getMessage() + " \n " + e.getCause());
            return new ResponseEntity<>("Roles could not fetched...", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * add role to db
     *
     * @param role
     * @return Role object
     * @author Fawad khan
     * @Created Date 31-0ct-2021
     */
    public ResponseEntity<Object> addRole(Roles role) {
        try {
            role.setCreatedDate(DateTime.getDateTime());
            roleRepository.save(role);
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Role already exist , Duplicates not allowed", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.debug("some error has occurred trying to save role, in Class  RoleService and its function addRole ",
                    e.getCause(), e.getMessage());
            System.out.println(e.getMessage() + " \n " + e.getCause());
            return new ResponseEntity<>("Roles could not added...", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param id
     * @return role object
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    public ResponseEntity<Object> getRoleById(Long id) {
        try {
            Optional<Roles> role = roleRepository.findById(id);
            if (role.isPresent())
                return new ResponseEntity<>(role, HttpStatus.FOUND);
            else
                return new ResponseEntity<>("could not found role , Check id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(
                    "some error has occurred during fetching Role by id , in class RoleService and its function getRoleById ",
                    e);
            return new ResponseEntity<>("Unable to find Role, an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * @param roles
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    public ResponseEntity<Object> updateRole(List<Roles> roles) {
        try {
            for (Roles role : roles) {

                role.setCreatedDate(DateTime.getDateTime());
                roleRepository.save(role);
            }
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            log.error(
                    "some error has occurred while trying to update role,, in class RoleService and its function updateRole ",
                    e.getMessage());
            return new ResponseEntity<>("Roles could not be Updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    public ResponseEntity<Object> deleteRole(Long id) {
        try {
            Optional<Roles> role = roleRepository.findById(id);
            if (role.isPresent()) {

                // set status false
                role.get().setActive(false);
                // set updated date
                role.get().setUpdatedDate(DateTime.getDateTime());
                roleRepository.save(role.get());
                return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("Role does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           log.error(
                    "some error has occurred while trying to Delete role,, in class RoleService and its function deleteRole ",
                    e.getMessage(), e.getCause(), e);
            return new ResponseEntity<>("Role could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
