package myapp.ebank.controller;

import myapp.ebank.model.entity.Organizations;
import myapp.ebank.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private static final String defaultAuthValue = "12345";
    OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * check organization is authorized or not
     *
     * @param authValue
     * @return
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * @param authValue
     * @param organization
     * @return added organization object
     * @author Fawad khan
     * @createdDate 01-nov-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addOrganization(@RequestHeader(value = "Authorization") String authValue,
                                                  @RequestBody Organizations organization) {
        // check authorization
        if (authValue != null) {
            if (authorize(authValue)) {
                return organizationService.saveOrganization(organization);
            } else
                return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * @param authValue
     * @return list of users
     * @Author "Fawad khan"
     * @Description "Display all user from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrganizations(@RequestHeader(value = "Authorization") String authValue) {
        if (authorize(authValue)) {
            return organizationService.listAllOrganization();
        } else
            return new ResponseEntity<>(" Not authorize", HttpStatus.UNAUTHORIZED);
    }


    /**
     * @param authValue
     * @param id
     * @return organization object
     * @createdDate 01-nov-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getOrganization(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                  @PathVariable Long id) {
        if (authorize(authValue)) {
            return organizationService.getOrganizationById(id);
        } else {
            return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param organization
     * @return
     * @createdDate 01-nov-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateOrganization(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                     @RequestBody Organizations organization) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return organizationService.updateOrganization(organization);
            } else
                return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param id
     * @return
     * @createdDate 01-nov-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteOrganization(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                     @PathVariable Long id) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return organizationService.deleteOrganization(id);
            } else
                return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

}
