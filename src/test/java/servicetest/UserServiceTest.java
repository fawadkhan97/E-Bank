package servicetest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import myapp.ebank.model.entity.Users;
import myapp.ebank.repository.UserRepository;
import myapp.ebank.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;


    @SneakyThrows
    @Test
    @DisplayName("Test findById ")
    void testFindById() {
        Users user = new Users(1L, "fawadkhan", "fawad60", "+923125954041", "fawad45@gmail.com",
                "3710194563447", "asd", 20, "01-01-1998", new Date(), new Date(), true, 78555, null, null, null, null);
        when(userRepository.findByIdAndIsActive(user.getId(), user.isActive())).thenReturn(Optional.of(user));
        // Execute the service call
        ObjectNode returnedReponse = userService.getUserById(1L, "api/test");

        JsonNode returnedUser = returnedReponse.get("data");

        System.out.println(returnedUser);
        // Assert the response
        Assertions.assertTrue(String.valueOf(returnedReponse.get("status")).contains("200"), "response should be 200");
       // Assertions.assertSame(user.getId(), Integer.parseInt(String.valueOf(returnedUser.get("id"))), "The user returned was not the same as the mock");
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
