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
    private static final String defaultAuthValue = "12345";
    final private PermissionService permissionService;
      private static final Logger log = LogManager.getLogger(PermissionService.class);

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * check permission is authorized or not
     *
     * @param authValue
     * @return
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * @param authValue
     * @return
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllPermission(@RequestHeader("Authorization") String authValue) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return permissionService.getAllPermission();
            } else {
                return new ResponseEntity<>(": Not authorize", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getPermission(@RequestHeader("Authorization") String authValue,
                                                @PathVariable Long id) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return permissionService.getPermissionById(id);
            } else {
                return new ResponseEntity<>(": Not authorize", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param permissions
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addPermission(@RequestHeader("Authorization") String authValue,
                                              @Valid @RequestBody List<Permissions> permissions) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return permissionService.savePermission(permissions);
            } else {
                return new ResponseEntity<>(": Not authorize", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param permission
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updatePermission(@RequestHeader("Authorization") String authValue,
                                                  @Valid @RequestBody List<Permissions> permission) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return permissionService.updatePermission(permission);
            } else
                return new ResponseEntity<>(":  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePermission(@RequestHeader("Authorization") String authValue,
                                                   @PathVariable long id) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return permissionService.deletePermission(id);
            } else
                return new ResponseEntity<>(":  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }
}
