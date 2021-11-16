package myapp.ebank.controller;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.model.entity.Loans;
import myapp.ebank.model.entity.Users;
import myapp.ebank.service.LoanService;
import myapp.ebank.service.UserService;
import myapp.ebank.util.ResponseMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private static final String defaultAuthValue = "12345";
    private final String notAuthorize = "Not Authorize";
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
    public ResponseEntity<Object> getAllUsers(@RequestHeader(value = "Authorization") String authValue, HttpServletRequest httpServletRequest) throws ParseException {

        if (authorize(authValue)) {
            return userService.listAllUser(httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);
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
                                          @Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {
        // check authorization
        if (authorize(authValue)) {
            return userService.saveUser(user, httpServletRequest);
        } else {
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param id
     * @createdDate 31-oct-2021
     */
    @PostMapping("/{id}/sendToken")
    public ResponseEntity<Object> sendToken(@RequestHeader(value = "Authorization") String authValue,
                                            @PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {

        if (authorize(authValue)) {
            return userService.sendToken(id, httpServletRequest);
        } else {
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);
        }


    }

    /**
     * @param userid
     * @param token
     * @return String of User verified or not
     * @createdDate 14-oct-2021
     */
    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestHeader(value = "userid") Long userid,
                                             @RequestHeader(value = "token") int token, HttpServletRequest httpServletRequest) {
        return userService.verifyUser(userid, token, httpServletRequest);
    }

    /**
     * @param authValue
     * @param id
     * @return user object
     * @createdDate 27-oct-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUser(@RequestHeader(value = "Authorization") String authValue,
                                          @PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {

        if (authorize(authValue)) {
            return userService.getUserById(id, httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param user
     * @return
     * @createdDate 27-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestHeader(value = "Authorization") String authValue,
                                             @Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {
        if (authorize(authValue)) {
            return userService.updateUser(user, httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);

    }


    /**
     * @param authValue
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteUser(@RequestHeader(value = "Authorization") String authValue,
                                             @PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        if (authorize(authValue)) {
            return userService.deleteUser(id, httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);

    }

    /**
     * @param authValue
     * @param userid
     * @param loan
     * @return
     */
    @PostMapping("/{userid}/applyForLoan")
    public ResponseEntity<Object> applyForLoan(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, @Valid @RequestBody Loans loan, HttpServletRequest httpServletRequest) throws ParseException {
        if (authorize(authValue)) {
            return userService.applyForLoan(userid, loan, httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);

    }

    /**
     * @param authValue
     * @param userid
     * @param loan
     * @return
     */
    @PostMapping("/{userid}/depositLoan")
    public ResponseEntity<Object> depositLoan(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, @Valid @RequestBody Loans loan, HttpServletRequest httpServletRequest) throws ParseException {
        if (authorize(authValue)) {
            return userService.depositLoan(userid, loan, httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param userid
     * @param funds
     * @return
     */
    @PostMapping("/{userid}/applyForFunds")
    public ResponseEntity<Object> applyForFunds(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, @Valid @RequestBody Funds funds, HttpServletRequest httpServletRequest) throws ParseException {
        if (authorize(authValue)) {
            return userService.applyForFunds(userid, funds, httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);

    }

    /**
     * @param authValue
     * @param userid
     * @return
     */
    @GetMapping("/{userid}/getFundsAndLoans")
    public ResponseEntity<Object> getUserFundsAndLoans(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, HttpServletRequest httpServletRequest) throws ParseException {
        if (authorize(authValue)) {
            return userService.getUserFundsAndLoans(userid, httpServletRequest);
        } else
            return new ResponseEntity<>(ResponseMapping.ApiReponse(HttpStatus.UNAUTHORIZED, notAuthorize, httpServletRequest.getRequestURI(), null), HttpStatus.UNAUTHORIZED);

    }
}

