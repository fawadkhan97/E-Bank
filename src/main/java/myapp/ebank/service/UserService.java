package myapp.ebank.service;

import myapp.ebank.model.dto.UserFundsAndLoans;
import myapp.ebank.model.entity.Funds;
import myapp.ebank.model.entity.Loans;
import myapp.ebank.model.entity.Roles;
import myapp.ebank.model.entity.Users;
import myapp.ebank.repository.LoanRepository;
import myapp.ebank.repository.UserRepository;
import myapp.ebank.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type User service.
 */
@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    /**
     * The Loan repository.
     */
    final LoanRepository loanRepository;
    /**
     * The Feign police record service.
     */
    final FeignPoliceRecordService feignPoliceRecordService;
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;
    private final SMSUtil smsUtil = new SMSUtil();
    private final Double interestRate = 0.045;
    private final Double loanAmount = 5000000.0;
    private final Double fundAmount = 10000000.0;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * The Unpaid loan count.
     */
    int unpaidLoanCount = 0;
    private Date expirationTime;


    /**
     * Instantiates a new User service.
     *
     * @param userRepository           the user repository
     * @param emailUtil                the email util
     * @param loanRepository           the loan repository
     * @param feignPoliceRecordService the feign police record service
     * @param bCryptPasswordEncoder    the b crypt password encoder
     */
// Autowiring through constructor
    public UserService(UserRepository userRepository, EmailUtil emailUtil, LoanRepository loanRepository, FeignPoliceRecordService feignPoliceRecordService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.emailUtil = emailUtil;
        this.loanRepository = loanRepository;
        this.feignPoliceRecordService = feignPoliceRecordService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * List all user response entity.
     *
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> listAllUser(HttpServletRequest httpServletRequest) throws ParseException {
        try {
            List<Users> users = userRepository.findAllByIsActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (users.isEmpty()) {
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "  Users are empty", httpServletRequest.getRequestURI(), null), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.OK, "users found", httpServletRequest.getRequestURI(), users), HttpStatus.OK);
            }

        } catch (Exception e) {
            log.info("some error has occurred trying to Fetch users, in Class  UserService and its function listAllUser \t{} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Some error occurred..Users could not be found", httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUserName(userName);
        if (user.isPresent() && user.get().isActive()) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Roles role : user.get().getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                log.info("granted authorities are:  {}", role.getName());
            }
            log.info("granted authorities are:  {}", grantedAuthorities);

            return new User(user.get().getUserName(), user.get().getPassword(), grantedAuthorities);
        } else
            log.info("exception occurred in load by username parameter is {}", userName);
        throw new UsernameNotFoundException("user not found " + userName);
    }


    /**
     * Gets user by id.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the user by id
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> getUserById(Long id, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Optional<Users> user = userRepository.findByIdAndIsActive(id, true);
            if (user.isPresent()) {
                // check if user is verified
                log.info("user fetch and found from db by id  : {} ", user);
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.OK, "user found", httpServletRequest.getRequestURI(), user), HttpStatus.OK);
            } else {
                log.info("no user found with id:{}", id);
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "could not found user with given details.... user may not be verified", httpServletRequest.getRequestURI(), null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching Users by id , in class UserService and its function getUserById {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Some error occurred..Users could not be found", httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    /**
     * Save user response entity.
     *
     * @param user               the user
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> saveUser(Users user, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            //   Boolean criminalRecord = feignPoliceRecordService.checkCriminalRecord("61101-7896541-5");
            //  log.info("record is from kamran :" + criminalRecord);
            Date date = DateTime.getDateTime();
            expirationTime = DateTime.getExpireTime(2);
            user.setCreatedDate(date);
            user.setUpdatedDate(null);
            user.setActive(false);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Random rndkey = new Random(); // Generating a random number
            int token = rndkey.nextInt(999999); // Generating a random email token of 6 digits
            user.setToken(token);
            // save user to db
            userRepository.save(user);
            // send email token to user email and save in db
            emailUtil.sendMail(user.getEmail(), token);
            // send sms token to user email and save in db
            smsUtil.sendSMS(user.getPhoneNumber(), token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            log.info("error is   {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.BAD_REQUEST, e.getRootCause().getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            log.info("some error has occurred while trying to save user,, in class UserService and its function saveUser {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Update user response entity.
     *
     * @param user               the user
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> updateUser(Users user, HttpServletRequest httpServletRequest) throws ParseException {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("userid", user.getId());
        responseMap.put("username", "fawad");
        try {
            user.setUpdatedDate(DateTime.getDateTime());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.info(
                    "some error has occurred while trying to update user,, in class UserService and its function updateUser {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete user response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> deleteUser(Long id, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Optional<Users> user = userRepository.findByIdAndIsActive(id, true);
            if (user.isPresent()) {
                // set status false
                user.get().setActive(false);
                // set updated date
                user.get().setUpdatedDate(DateTime.getDateTime());
                userRepository.save(user.get());
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.OK, "  Users deleted successfully", httpServletRequest.getRequestURI(), null), HttpStatus.OK);

            } else
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "  Users does not exists ", httpServletRequest.getRequestURI(), null), HttpStatus.OK);
        } catch (Exception e) {
            log.info(
                    "some error has occurred while trying to Delete user, in class UserService and its function deleteUser   {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Verify user response entity.
     *
     * @param id                 the id
     * @param token              the token
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> verifyUser(Long id, int token, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Optional<Users> user = userRepository.findByIdAndToken(id, token);
            Date date = DateTime.getDateTime();
            if (user.isPresent()) {
                //check if token is not expired
                if (date.after(expirationTime)) {
                    log.info("token has expired");
                    return new ResponseEntity<>("Token verification time has expire please regenerate verification token ", HttpStatus.METHOD_NOT_ALLOWED);
                }
                log.info("user is : {}", user);
                user.get().setActive(true);
                userRepository.save(user.get());
                return new ResponseEntity<>("user has been verified ", HttpStatus.OK);
            } else
                return new ResponseEntity<>("incorrect verification details were entered or verification time has expired", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(String.valueOf(e.getCause()), e.getMessage());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Send token response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> sendToken(Long id, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Optional<Users> user = userRepository.findById(id);
            if (user.isPresent()) {
                expirationTime = DateTime.getExpireTime(5);
                Random rndkey = new Random(); // Generating a random number
                int token = rndkey.nextInt(999999); // Generating a random email token of 6 digits
                user.get().setToken(token);
                userRepository.save(user.get());
                smsUtil.sendSMS(user.get().getPhoneNumber(), token);
                emailUtil.sendMail(user.get().getEmail(), token);
                return new ResponseEntity<>("token has been sent successfully please use it to verify ", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching User by id , in class UserService and its function sendSms  {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Apply for loan response entity.
     *
     * @param id                 the id
     * @param loan               the loan
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Object> applyForLoan(Long id, Loans loan, HttpServletRequest httpServletRequest) throws Exception {
        try {
            if (loan.getLoanAmount() < loanAmount) {
                Optional<Users> user = userRepository.findById(id);
                if (user.isPresent() && user.get().isActive()) {
                    List<Loans> loans = new ArrayList<>(user.get().getLoans());
                    // count total unpaid loans of user
                    unpaidLoanCount = (int) loans.stream().filter(loans1 ->!loans1.getPaidStatus()).count();
                        if (unpaidLoanCount > 2) {
                            return new ResponseEntity<>("user has already pending unpaid loans", HttpStatus.METHOD_NOT_ALLOWED);
                        }

                    log.info("user fetch and found from db by id  {}: ", user);
                    loan.setCreatedDate(DateTime.getDateTime());
                    loan.setInterestRate(interestRate);
                    loan.setTotalAmountToBePaid(loan.getLoanAmount() + (interestRate * loan.getLoanAmount()));
                    loan.setDueDate(DateTime.getDueDate(5));
                    loan.setAmountPaid(0.0);
                    loan.setPaidStatus(false);
                    loan.setPaidDate(null);
                    loan.setActive(true);

                    loans.add(loan);
                    user.get().setLoans(loans);
                    // save loan to db first then save user
                   loanRepository.save(loan);
                    userRepository.save(user.get());
                    return new ResponseEntity<>(loan, HttpStatus.OK);
                } else {
                    log.info("no user found with id: {}", id);
                    return new ResponseEntity<>("could not found user with given details.... user may not be verified", HttpStatus.NOT_FOUND);
                }
            } else
                return new ResponseEntity<>("maximum allowed limit of loan is {loanAmount} ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("an error has occurred in userService method apply for loan  {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Deposit loan response entity.
     *
     * @param id                 the id
     * @param loan               the loan
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> depositLoan(Long id, Loans loan, HttpServletRequest httpServletRequest) throws ParseException {

        try {
            Optional<Users> user = userRepository.findById(id);
            Optional<Loans> fetchUserLoans = loanRepository.findById(loan.getId());
            if (user.isPresent() && user.get().isActive() && fetchUserLoans.isPresent()) {
                List<Loans> loansList = new ArrayList<>(user.get().getLoans());
                for (Loans loan1 : loansList) {
                    // check if user contain  the loan specified
                    if (loan1.getId() == loan.getId() && !loan1.getPaidStatus()) {
                        double paidAmount = loan.getAmountPaid() - loan1.getTotalAmountToBePaid();
                        if (paidAmount > 0 || paidAmount < 0) {
                            return new ResponseEntity<>("paid amount is not equal to amount to be paid , please enter correct amount and try again", HttpStatus.FORBIDDEN);
                        }
                        if (Objects.equals(loan.getAmountPaid(), loan1.getTotalAmountToBePaid())) {
                            loan1.setPaidStatus(true);
                            loan1.setAmountPaid(loan.getAmountPaid());
                            loan1.setPaidDate(DateTime.getDateTime());
                            loanRepository.save(loan1);
                            return new ResponseEntity<>("loan paid successfully", HttpStatus.OK);
                        }
                    } else
                        return new ResponseEntity<>("loan could not be paid..it may have been paid already", HttpStatus.BAD_REQUEST);
                }
            } else if (fetchUserLoans.isEmpty()) {
                return new ResponseEntity<>("loans does not exist for given id", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("could not found any user record with given details.... user may not be verified", HttpStatus.BAD_REQUEST);

        } catch (
                Exception e) {
            log.info("an error has occurred in userService method depositLoan  {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Apply for funds response entity.
     *
     * @param id                 the id
     * @param funds              the funds
     * @param httpServletRequest the http servlet request
     * @return the response entity
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> applyForFunds(Long id, Funds funds, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Optional<Users> user = userRepository.findById(id);
            if (user.isPresent() && user.get().isActive() && user.get().getOrganization().getType().equalsIgnoreCase("government")) {
                // check if user is  verified
                List<Funds> fundsList = new ArrayList<>(user.get().getFunds());
                if (funds.getAmount() > fundAmount) {
                    funds.setCreatedDate(DateTime.getDateTime());
                    funds.setApprovedStatus(false);
                    fundsList.add(funds);
                    user.get().setFunds(fundsList);
                    userRepository.save(user.get());
                    return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.OK, "Funds requested has been received..it will be process and its status will be updated soon", httpServletRequest.getRequestURI(), null), HttpStatus.OK);

                }
                funds.setCreatedDate(DateTime.getDateTime());
                funds.setActive(true);
                funds.setApprovedStatus(true);
                fundsList.add(funds);
                user.get().setFunds(fundsList);
                userRepository.save(user.get());
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.OK, "funds has been approved ", httpServletRequest.getRequestURI(), null), HttpStatus.OK);

            } else if (user.isPresent() && !user.get().getOrganization().getType().equalsIgnoreCase("government")) {
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.BAD_REQUEST, "only government departments can apply for funds", httpServletRequest.getRequestURI(), null), HttpStatus.OK);
            } else
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "could not found user with given details.... user may not be verified", httpServletRequest.getRequestURI(), null), HttpStatus.OK);

        } catch (
                Exception e) {
            log.info("error is {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Gets user funds and loans.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the user funds and loans
     * @throws ParseException the parse exception
     */
    public ResponseEntity<Object> getUserFundsAndLoans(Long id, HttpServletRequest httpServletRequest) throws ParseException {
        try {
            Optional<Users> user = userRepository.findByIdAndIsActive(id, true);
            if (user.isPresent()) {
                // save Funds and Loans from user object
                UserFundsAndLoans userFundsAndLoans = new UserFundsAndLoans();
                userFundsAndLoans.setFunds(user.get().getFunds());
                userFundsAndLoans.setLoans(user.get().getLoans());
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.OK, "Success", httpServletRequest.getRequestURI(), userFundsAndLoans), HttpStatus.OK);

            } else
                return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.NOT_FOUND, "user may not exists for given id", httpServletRequest.getRequestURI(), null), HttpStatus.OK);

        } catch (Exception e) {
            log.info(
                    "some error has occurred during fetching User by id , in class UserService and its function getUserFundsAndLoans {} .... {}", e.getMessage(), e.getCause());
            return new ResponseEntity<>(ResponseMapping.apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}