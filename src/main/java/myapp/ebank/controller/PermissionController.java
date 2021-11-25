package myapp.ebank.controller;

import myapp.ebank.model.entity.Permissions;
import myapp.ebank.service.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * The type Permission controller.
 */
@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {
    final private PermissionService permissionService;
    private static final Logger log = LogManager.getLogger(PermissionService.class);

    /**
     * Instantiates a new Permission controller.
     *
     * @param permissionService the permission service
     */
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    /**
     * Gets all permission.
     *
     * @param authValue the auth value
     * @return the all permission
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllPermission(@RequestHeader("Authorization") String authValue) {
        return permissionService.getAllPermission();
    }

    /**
     * Gets permission.
     *
     * @param authValue the auth value
     * @param id        the id
     * @return the permission
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getPermission(@RequestHeader("Authorization") String authValue,
                                                @PathVariable Long id) {
        return permissionService.getPermissionById(id);
    }

    /**
     * Add permission response entity.
     *
     * @param authValue   the auth value
     * @param permissions the permissions
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addPermission(@RequestHeader("Authorization") String authValue,
                                                @Valid @RequestBody List<Permissions> permissions) {

        return permissionService.savePermission(permissions);

    }

    /**
     * Update permission response entity.
     *
     * @param authValue  the auth value
     * @param permission the permission
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updatePermission(@RequestHeader("Authorization") String authValue,
                                                   @Valid @RequestBody List<Permissions> permission) {

        return permissionService.updatePermission(permission);

    }

    /**
     * Delete permission response entity.
     *
     * @param authValue the auth value
     * @param id        the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePermission(@RequestHeader("Authorization") String authValue,
                                                   @PathVariable long id) {

        return permissionService.deletePermission(id);

    }
}
