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
 * @author fawad khan
 * @createdDate 31-oct-2021
 */
@RestController
@RequestMapping("/role")
@Validated
public class RoleController {
    private static final String defaultAuthValue = "12345";
    final private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    /**
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoles(
            @RequestHeader(required = false, value = "Authorization") String authValue) {
        return roleService.getAllRoles();


    }

    /**
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getRole(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
        return roleService.getRoleById(id);

    }

    /**
     * @param role
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> saveRole(@RequestHeader(required = false, value = "Authorization") String authValue,
                                           @Valid @RequestBody Roles role) {

        return roleService.addRole(role);


    }

    /**
     * @param roles
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateRole(@RequestHeader("Authorization") String authValue,
                                             @Valid @RequestBody List<Roles> roles) {


        return roleService.updateRole(roles);

    }

    /**
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRole(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
        return roleService.deleteRole(id);

    }

}