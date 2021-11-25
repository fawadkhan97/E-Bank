package myapp.ebank.service;

import myapp.ebank.model.entity.Permissions;
import myapp.ebank.repository.PermissionRepository;
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
 * The type Permission service.
 */
@Service
public class PermissionService {
    private static final Logger log = LogManager.getLogger(PermissionService.class);
    final private PermissionRepository permissionRepository;

    /**
     * Instantiates a new Permission service.
     *
     * @param permissionRepository the permission repository
     */
// autowiring permissionRepository
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Gets all permission.
     *
     * @return the all permission
     */
    public ResponseEntity<Object> getAllPermission() {

        try {
            List<Permissions> permissions = permissionRepository.findAllByIsActiveOrderByCreatedDateDesc(true);
            if (!permissions.isEmpty()) {
                return new ResponseEntity<>(permissions, HttpStatus.OK);
            } else
                return new ResponseEntity<>(" no permission is available ", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            System.out.println(e.getMessage() + " \n " + e.getCause());
            log.info(
                    "some error has occurred trying to Fetch Permissions, in Class  PermissionService and its function getALLPermissions ",
                    e.getCause(), e.getMessage());
            return new ResponseEntity<>(" Could not fetch permissions due to some error",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets permission by id.
     *
     * @param id the id
     * @return the permission by id
     */
    public ResponseEntity<Object> getPermissionById(Long id) {
        try {
            Optional<Permissions> permission = permissionRepository.findById(id);
            if (permission.isPresent())
                return new ResponseEntity<>(permission, HttpStatus.FOUND);
            else
                return new ResponseEntity<>("could not found permission , Check id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching Permission by id , in class PermissionService and its function getPermissionById ",
                    e);

            return new ResponseEntity<>("Unable to find Permission, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Save permission response entity.
     *
     * @param permissions the permissions
     * @return the response entity
     */
    public ResponseEntity<Object> savePermission(List<Permissions> permissions) {
        try {
            for (Permissions permission : permissions) {

                permission.setCreatedDate(DateTime.getDateTime());
                permission.setActive(true);
                permissionRepository.save(permission);
            }
            return new ResponseEntity<>(permissions, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Could not add new permission , Permission already exist or some data fields might be missing ", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " \n " + e.getCause());
            log.info(
                    "some error has occurred trying to add Permissions, in Class  PermissionService and its function savePermissions ",
                    e.getCause(), e.getMessage());
            return new ResponseEntity<>("Could not add permissions due to some  error",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update permission response entity.
     *
     * @param permissions the permissions
     * @return the response entity
     */
    public ResponseEntity<Object> updatePermission(List<Permissions> permissions) {
        try {
            for (Permissions permission : permissions) {
                permission.setUpdatedDate(DateTime.getDateTime());
                permissionRepository.save(permission);
            }
            return new ResponseEntity<>(permissions, HttpStatus.OK);
        } catch (Exception e) {
            log.info(
                    "some error has occurred while trying to update permission,, in class PermissionService and its function updatePermission ",
                    e.getMessage());
            return new ResponseEntity<>("Permissions could not be Updated , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete permission response entity.
     *
     * @param id the id
     * @return the response entity
     */
    public ResponseEntity<String> deletePermission(Long id) {
        try {
            Optional<Permissions> permission = permissionRepository.findByIdAndIsActive(id,true);
            if (permission.isPresent()) {
                // set status false
                permission.get().setActive(false);
                // set updated date
                permission.get().setUpdatedDate(DateTime.getDateTime());
                permissionRepository.save(permission.get());
                return new ResponseEntity<>("Permission deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>("Permission does not exists ", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.info(
                    "some error has occurred trying to Delete Permissions, in Class  PermissionService and its function deletePermissions ",
                    e.getCause(), e.getMessage());
            return new ResponseEntity<>("Permission could not be Deleted..Due to some error.....",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
