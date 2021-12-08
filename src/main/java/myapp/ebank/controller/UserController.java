package myapp.ebank.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import myapp.ebank.model.dto.AuthenticationRequest;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;


/**
 * The type User controller.
 */
@RestController
@RequestMapping("user")
@Validated
public class UserController {
    final private UserService userService;
    final private LoanService loanService;
     private final AuthenticationManager authenticationManager;

    /**
     * Instantiates a new User controller.
     *
     * @param userService           the user service
     * @param loanService           the loan service
     * @param authenticationManager the authentication manager
     */
    public UserController(UserService userService, LoanService loanService, AuthenticationManager authenticationManager
                         ) {
        this.userService = userService;
        this.loanService = loanService;
        this.authenticationManager = authenticationManager;
    }


    /**
     * Login response entity.
     *
     * @param authenticationRequest the authentication request
     * @return the response entity
     * @throws Exception the exception
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
     * Gets all users.
     *
     * @param httpServletRequest the http servlet request
     * @return the all users
     * @throws ParseException the parse exception
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(HttpServletRequest httpServletRequest) throws ParseException {
        return userService.listAllUser(httpServletRequest.getRequestURI());
    }

    /**
     * Add user response entity.
     *
     * @param user               the user
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.saveUser(user, httpServletRequest.getRequestURI());
    }

    /**
     * Send token response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    @PostMapping("/{id}/sendToken")
    public ResponseEntity<Object> sendToken(@PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.sendToken(id, httpServletRequest.getRequestURI());
    }

    /**
     * Verify user response entity.
     *
     * @param userid             the userid
     * @param token              the token
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestHeader(value = "userid") Long userid,
                                             @RequestHeader(value = "token") int token, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.verifyUser(userid, token, httpServletRequest.getRequestURI());
    }

    /**
     * Gets user.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the user
     * @throws ParseException the parse exception
     */
    @GetMapping("/get/{id}")
    public ObjectNode getUser(@PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.getUserById(id, httpServletRequest.getRequestURI());
    }

    /**
     * Update user response entity.
     *
     * @param user               the user
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody Users user, HttpServletRequest httpServletRequest) throws ParseException {

        return userService.updateUser(user, httpServletRequest.getRequestURI());

    }


    /**
     * Delete user response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.deleteUser(id, httpServletRequest.getRequestURI());

    }

    /**
     * Apply for loan response entity.
     *
     * @param userid             the userid
     * @param loan               the loan
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/{userid}/applyForLoan")
    public ResponseEntity<Object> applyForLoan(@PathVariable Long userid, @Valid @RequestBody Loans loan, HttpServletRequest httpServletRequest) throws Exception {
        return userService.applyForLoan(userid, loan, httpServletRequest.getRequestURI());
    }

    /**
     * Deposit loan response entity.
     *
     * @param authValue          the auth value
     * @param userid             the userid
     * @param loan               the loan
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    @PostMapping("/{userid}/depositLoan")
    public ResponseEntity<Object> depositLoan( @PathVariable Long userid, @Valid @RequestBody Loans loan, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.depositLoan(userid, loan, httpServletRequest.getRequestURI());
    }

    /**
     * Apply for funds response entity.
     *
     * @param authValue          the auth value
     * @param userid             the userid
     * @param funds              the funds
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    @PostMapping("/{userid}/applyForFunds")
    public ResponseEntity<Object> applyForFunds( @PathVariable Long userid, @Valid @RequestBody Funds funds, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.applyForFunds(userid, funds, httpServletRequest.getRequestURI());

    }

    /**
     * Gets user funds and loans.
     *
     * @param authValue          the auth value
     * @param userid             the userid
     * @param httpServletRequest the http servlet request
     * @return the user funds and loans
     * @throws ParseException the parse exception
     */
    @GetMapping("/{userid}/getFundsAndLoans")
    public ResponseEntity<Object> getUserFundsAndLoans( @PathVariable Long userid, HttpServletRequest httpServletRequest) throws ParseException {
        return userService.getUserFundsAndLoans(userid, httpServletRequest.getRequestURI());

    }
}

