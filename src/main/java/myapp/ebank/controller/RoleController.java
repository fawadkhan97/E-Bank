package myapp.ebank.controller;


import myapp.ebank.model.entity.Roles;
import myapp.ebank.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Role controller.
 */
@RestController
@RequestMapping("/role")
@Validated
public class RoleController {
    private static final String defaultAuthValue = "12345";
    final private RoleService roleService;

    /**
     * Instantiates a new Role controller.
     *
     * @param roleService the role service
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    /**
     * Gets all roles.
     *
     * @param authValue the auth value
     * @return the all roles
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoles(
            @RequestHeader(required = false, value = "Authorization") String authValue) {
        return roleService.getAllRoles();


    }

    /**
     * Gets role.
     *
     * @param authValue the auth value
     * @param id        the id
     * @return the role
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getRole(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
        return roleService.getRoleById(id);

    }

    /**
     * Save role response entity.
     *
     * @param authValue the auth value
     * @param role      the role
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> saveRole(@RequestHeader(required = false, value = "Authorization") String authValue,
                                           @Valid @RequestBody Roles role) {

        return roleService.addRole(role);


    }

    /**
     * Update role response entity.
     *
     * @param authValue the auth value
     * @param roles     the roles
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateRole(@RequestHeader("Authorization") String authValue,
                                             @Valid @RequestBody List<Roles> roles) {


        return roleService.updateRole(roles);

    }

    /**
     * Delete role response entity.
     *
     * @param authValue the auth value
     * @param id        the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRole(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
        return roleService.deleteRole(id);

    }

}