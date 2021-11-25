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

@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {
    final private PermissionService permissionService;
    private static final Logger log = LogManager.getLogger(PermissionService.class);

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    /**
     * @return
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllPermission(@RequestHeader("Authorization") String authValue) {
        return permissionService.getAllPermission();
    }

    /**
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getPermission(@RequestHeader("Authorization") String authValue,
                                                @PathVariable Long id) {
        return permissionService.getPermissionById(id);
    }

    /**
     * @param permissions
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addPermission(@RequestHeader("Authorization") String authValue,
                                                @Valid @RequestBody List<Permissions> permissions) {

        return permissionService.savePermission(permissions);

    }

    /**
     * @param permission
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updatePermission(@RequestHeader("Authorization") String authValue,
                                                   @Valid @RequestBody List<Permissions> permission) {

        return permissionService.updatePermission(permission);

    }

    /**
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePermission(@RequestHeader("Authorization") String authValue,
                                                   @PathVariable long id) {

        return permissionService.deletePermission(id);

    }
}
