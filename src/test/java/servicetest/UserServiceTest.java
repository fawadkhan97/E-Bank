package servicetest;

import lombok.SneakyThrows;
import myapp.ebank.model.entity.Users;
import myapp.ebank.repository.UserRepository;
import myapp.ebank.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    UserService userService;

    @Mock
    UserRepository userRepository;

    HttpServletRequest httpServletRequest;

    @BeforeEach
    void initUseCase() {
        userService = new UserService(userRepository, null, null, null, null);
    }

    @SneakyThrows
    @Test
    @DisplayName("Test findById ")
    void testFindById() {
        Users user = new Users(1L, "fawadkhan", "fawad60", "+923125954041", "fawad45@gmail.com", "3710194563447", "asd", 20, "01-01-1998", new Date(), new Date(), true, 78555);
        when(userRepository.findByIdAndIsActive(user.getId(),user.isActive())).thenReturn(Optional.of(user));
        // Execute the service call

     ResponseEntity<Object> returnedUsers = userService.getUserById(1L, httpServletRequest);


        // Assert the response
        Assertions.assertEquals(404, returnedUsers.getStatusCodeValue(), "Users was not found");
        Assertions.assertSame(returnedUsers.getBody(), user, "The user returned was not the same as the mock");
    }
/*

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        // Setup our mock repository
        doReturn(Optional.empty()).when(repository).findById(1l);

        // Execute the service call
        Optional<Users> returnedUsers = service.findById(1l);

        // Assert the response
        Assertions.assertFalse(returnedUsers.isPresent(), "Users should not be found");
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        // Setup our mock repository
        Users user1 = new Users(1l, "Users Name", "Description", 1);
        Users user2 = new Users(2l, "Users 2 Name", "Description 2", 4);
        doReturn(Arrays.asList(user1, user2)).when(repository).findAll();

        // Execute the service call
        List<Users> users = service.findAll();

        // Assert the response
        Assertions.assertEquals(2, users.size(), "findAll should return 2 users");
    }

    @Test
    @DisplayName("Test save user")
    void testSave() {
        // Setup our mock repository
        Users user = new Users(1l, "Users Name", "Description", 1);
        doReturn(user).when(repository).save(any());

        // Execute the service call
        Users returnedUsers = service.save(user);

        // Assert the response
        Assertions.assertNotNull(returnedUsers, "The saved user should not be null");
        Assertions.assertEquals(2, returnedUsers.getVersion(), "The version should be incremented");
    }

*/


}
