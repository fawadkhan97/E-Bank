package myapp.ebank.controller;

import myapp.ebank.model.entity.Organizations;
import myapp.ebank.service.OrganizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organization")
@Validated
public class OrganizationController {
    OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * @param organization
     * @return added organization object
     * @author Fawad khan
     * @createdDate 01-nov-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addOrganization(@Validated @RequestBody Organizations organization) {

        return organizationService.saveOrganization(organization);
    }

    /**
     * @return list of users
     * @Author "Fawad khan"
     * @Description "Display all user from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrganizations(@RequestHeader(value = "Authorization") String authValue) {
        return organizationService.listAllOrganization();
    }

    /**
     * @param id
     * @return organization object
     * @createdDate 01-nov-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getOrganization(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                  @PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    /**
     * @param organization
     * @return
     * @createdDate 01-nov-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateOrganization(@Valid @RequestBody Organizations organization) {
        return organizationService.updateOrganization(organization);
    }

    /**
     * @param id
     * @return
     * @createdDate 01-nov-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteOrganization(@PathVariable Long id) {
        return organizationService.deleteOrganization(id);
    }

}
