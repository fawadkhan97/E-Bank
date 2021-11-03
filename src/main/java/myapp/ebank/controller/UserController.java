package myapp.ebank.controller;


import myapp.ebank.model.Funds;
import myapp.ebank.model.Loans;
import myapp.ebank.model.Users;
import myapp.ebank.service.LoanService;
import myapp.ebank.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final String defaultAuthValue = "12345";
    private static final Logger log = LogManager.getLogger();
    final UserService userService;
    final LoanService loanService;

    public UserController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    /**
     * check user is authorized or not
     *
     * @param authValue
     * @return
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * @param userName
     * @param password
     * @return
     * @Author "Fawad"
     * @Description "Login it takes username and password from frontend then check
     * from database by calling object with email"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam(value = "username") String userName,
                                        @RequestParam(value = "password") String password) {
        return userService.getUserByNameAndPassword(userName, password);
    }

    /**
     * @param authValue
     * @return list of users
     * @Author "Fawad khan"
     * @Description "Display all user from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(@RequestHeader(value = "Authorization") String authValue) {

        if (authorize(authValue)) {
            return userService.listAllUser();
        } else
            return new ResponseEntity<>(" Not authorize", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param user
     * @return added user object
     * @author Fawad khan
     * @createdDate 27-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestHeader(value = "Authorization") String authValue,
                                          @RequestBody Users user) {
        // check authorization
        if (authorize(authValue)) {
            return userService.saveUser(user);
        } else {
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param id
     * @createdDate 31-oct-2021
     */
    @PostMapping("/{id}/sendToken")
    public ResponseEntity<Object> sendToken(@RequestHeader(value = "Authorization") String authValue,
                                            @PathVariable Long id) {
        if (authorize(authValue)) {
            return userService.sendToken(id);
        } else {
            return new ResponseEntity<>("Not authorize", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param userid
     * @param token
     * @return String of User verified or not
     * @createdDate 14-oct-2021
     */
    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestHeader(value = "id") Long userid,
                                             @RequestHeader(value = "token") int token) {
        return userService.verifyUser(userid, token);
    }

    /**
     * @param authValue
     * @param id
     * @return user object
     * @createdDate 27-oct-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUser(@RequestHeader(value = "Authorization", required = false) String authValue,
                                          @PathVariable Long id) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return userService.getUserById(id);
            } else {
                return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param user
     * @return
     * @createdDate 27-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestHeader(value = "Authorization", required = false) String authValue,
                                             @RequestBody Users user) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return userService.updateUser(user);
            } else
                return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteUser(@RequestHeader(value = "Authorization", required = false) String authValue,
                                             @PathVariable Long id) {
        if (authorize(authValue)) {
            return userService.deleteUser(id);
        } else
            return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/{userid}/applyForLoan")
    public ResponseEntity<Object> applyForLoan(@PathVariable Long userid, @RequestBody Loans loan) {
        return userService.applyForLoan(userid, loan);
    }
  @PostMapping("/{userid}/depositLoan")
    public ResponseEntity<Object> depositLoan(@PathVariable Long userid, @RequestBody Loans loan) {
        return userService.applyForLoan(userid, loan);
    }

    @PostMapping("/{userid}/applyForFunds")
    public ResponseEntity<Object> applyForFunds(@PathVariable Long userid, @RequestBody Funds funds) {
        return userService.applyForFunds(userid, funds);
    }





}

