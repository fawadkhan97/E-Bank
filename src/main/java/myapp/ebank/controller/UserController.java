package myapp.ebank.controller;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.model.entity.Loans;
import myapp.ebank.model.entity.Users;
import myapp.ebank.service.LoanService;
import myapp.ebank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
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
    final UserService userService;
    final LoanService loanService;
    private final String notAuthorize = "Not Authorize";


    public UserController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    /**
     * check user is authorized or not
     *
     * @return
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * @param userName
     * @return
     */
    @PostMapping("/login")
    public UserDetails login(@RequestHeader String userName, @RequestHeader String password) {
        return userService.loadUserByUsername(userName);
    }

    /**
     * @return list of users
     * @Author "Fawad khan"
     * @Description "Display all user from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(HttpServletRequest httpServletRequest) throws ParseException {
        return userService.listAllUser(httpServletRequest);
    }

    /**
     * @param user
     * @return added user object
     * @author Fawad khan
     * @createdDate 27-oct-2021
     */
    @PreAuthorize("hasRole('user')")
    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {
        // check authorization
        return userService.saveUser(user, httpServletRequest);

    }

    /**
     * @param id
     * @createdDate 31-oct-2021
     */
    @PreAuthorize("hasRole('user')")
    @PostMapping("/{id}/sendToken")
    public ResponseEntity<Object> sendToken(@PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.sendToken(id, httpServletRequest);
    }

    /**
     * @param userid
     * @param token
     * @return String of User verified or not
     * @createdDate 14-oct-2021
     */
    @PreAuthorize("hasRole('user')")
    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestHeader(value = "userid") Long userid,
                                             @RequestHeader(value = "token") int token, HttpServletRequest httpServletRequest) {
        return userService.verifyUser(userid, token, httpServletRequest);
    }

    /**
     * @param id
     * @return user object
     * @createdDate 27-oct-2021
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.getUserById(id, httpServletRequest);
    }

    /**
     * @param user
     * @return
     * @createdDate 27-oct-2021
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {

        return userService.updateUser(user, httpServletRequest);

    }


    /**
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.deleteUser(id, httpServletRequest);

    }

    /**
     * @param userid
     * @param loan
     * @return
     */
    @PostMapping("/{userid}/applyForLoan")
    public ResponseEntity<Object> applyForLoan(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, @Valid @RequestBody Loans loan, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.applyForLoan(userid, loan, httpServletRequest);

    }

    /**
     * @param userid
     * @param loan
     * @return
     */
    @PostMapping("/{userid}/depositLoan")
    public ResponseEntity<Object> depositLoan(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, @Valid @RequestBody Loans loan, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.depositLoan(userid, loan, httpServletRequest);
    }

    /**
     * @param userid
     * @param funds
     * @return
     */
    @PostMapping("/{userid}/applyForFunds")
    public ResponseEntity<Object> applyForFunds(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, @Valid @RequestBody Funds funds, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.applyForFunds(userid, funds, httpServletRequest);

    }

    /**
     * @param userid
     * @return
     */
    @GetMapping("/{userid}/getFundsAndLoans")
    public ResponseEntity<Object> getUserFundsAndLoans(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.getUserFundsAndLoans(userid, httpServletRequest);

    }
}

