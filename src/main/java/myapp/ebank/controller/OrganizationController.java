package myapp.ebank.controller;

import myapp.ebank.model.entity.Organizations;
import myapp.ebank.service.OrganizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Organization controller.
 */
@RestController
@RequestMapping("/organization")
@Validated
public class OrganizationController {
    /**
     * The Organization service.
     */
    OrganizationService organizationService;

    /**
     * Instantiates a new Organization controller.
     *
     * @param organizationService the organization service
     */
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * Add organization response entity.
     *
     * @param organization the organization
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addOrganization(@Validated @RequestBody Organizations organization) {

        return organizationService.saveOrganization(organization);
    }

    /**
     * Gets all organizations.
     *
     * @param authValue the auth value
     * @return the all organizations
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrganizations(@RequestHeader(value = "Authorization") String authValue) {
        return organizationService.listAllOrganization();
    }

    /**
     * Gets organization.
     *
     * @param authValue the auth value
     * @param id        the id
     * @return the organization
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getOrganization(@RequestHeader(value = "Authorization", required = false) String authValue,
                                                  @PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    /**
     * Update organization response entity.
     *
     * @param organization the organization
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateOrganization(@Valid @RequestBody Organizations organization) {
        return organizationService.updateOrganization(organization);
    }

    /**
     * Delete organization response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteOrganization(@PathVariable Long id) {
        return organizationService.deleteOrganization(id);
    }

}
