package myapp.ebank.controller;

import myapp.ebank.model.dto.AuthenticationRequest;
import myapp.ebank.model.dto.AuthenticationResponse;
import myapp.ebank.model.entity.Funds;
import myapp.ebank.model.entity.Loans;
import myapp.ebank.model.entity.Users;
import myapp.ebank.service.LoanService;
import myapp.ebank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;


@RestController
@RequestMapping("user")
@Validated
public class UserController {
    final private UserService userService;
    final private LoanService loanService;
     private AuthenticationManager authenticationManager;

    public UserController(UserService userService, LoanService loanService, AuthenticationManager authenticationManager
                         ) {
        this.userService = userService;
        this.loanService = loanService;
        this.authenticationManager = authenticationManager;
    }


    /**
     * @param authenticationRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

       // final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

      //  final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok("new AuthenticationResponse(token)");

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /**
     * @return list of users
     * @Author "Fawad khan"
     * @Description "Display all user from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(HttpServletRequest httpServletRequest, OAuth2Authentication authentication) throws ParseException {
        return userService.listAllUser(httpServletRequest);
    }

    /**
     * @param user
     * @return added user object
     * @author Fawad khan
     * @createdDate 27-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.saveUser(user, httpServletRequest);
    }

    /**
     * @param id
     * @createdDate 31-oct-2021
     */
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
    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestHeader(value = "userid") Long userid,
                                             @RequestHeader(value = "token") int token, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.verifyUser(userid, token, httpServletRequest);
    }

    /**
     * @param id
     * @return user object
     * @createdDate 27-oct-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.getUserById(id, httpServletRequest);
    }

    /**
     * @param user
     * @return
     * @createdDate 27-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {

        return userService.updateUser(user, httpServletRequest);

    }


    /**
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
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
    public ResponseEntity<Object> applyForLoan(@RequestHeader(value = "Authorization") String authValue, @PathVariable Long userid, @Valid @RequestBody Loans loan, HttpServletRequest httpServletRequest) throws Exception {
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

