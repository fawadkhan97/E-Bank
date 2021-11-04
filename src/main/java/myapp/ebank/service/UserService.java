package myapp.ebank.service;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.model.entity.Loans;
import myapp.ebank.model.entity.Users;
import myapp.ebank.repository.LoanRepository;
import myapp.ebank.repository.UserRepository;
import myapp.ebank.util.DateTime;
import myapp.ebank.util.EmailUtil;
import myapp.ebank.util.SMSUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Fawad khan Created Date : 08-October-2021 A service class of user
 * connected with repository which contains user CRUD operations
 * @createdDate 27-oct-2021
 */
@Service
public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    final LoanRepository loanRepository;
    private final UserRepository userRepository;
    final FeignPoliceRecordService feignPoliceRecordService;
    private final EmailUtil emailUtil;
    private final SMSUtil smsUtil = new SMSUtil();
    private Date expirationTime;
    private Double totalAmountWithInterest;
    private Double interestRate = 0.045;

    // Autowiring through constructor
    public UserService(UserRepository userRepository, EmailUtil emailUtil, LoanRepository loanRepository, FeignPoliceRecordService feignPoliceRecordService) {
        this.userRepository = userRepository;
        this.emailUtil = emailUtil;
        this.loanRepository = loanRepository;
        this.feignPoliceRecordService = feignPoliceRecordService;
    }

    /**
     * @return List of users
     * @author Fawad khan
     */
    // Get list of all users
    public ResponseEntity<Object> listAllUser() {
        try {
            List<Users> users = userRepository.findAllByisActive(true);
            log.info("list of  users fetch from db are ", users);
            // check if list is empty
            if (users.isEmpty()) {
                return new ResponseEntity<>("  Users are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }

        } catch (Exception e) {
            log.error(
                    "some error has occurred trying to Fetch users, in Class  UserService and its function listAllUser ",
                    e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Users could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    // get user by specific id
    public ResponseEntity<Object> getUserById(Long id) {
        try {
            Optional<Users> user = userRepository.findById(id);
            if (user.isPresent() && user.get().isActive()) {
                // check if user is verified
                //              log.info("user fetch and found from db by id  : ", user.toString());
                return new ResponseEntity<>(user, HttpStatus.FOUND);
            } else
//                log.info("no user found with id:", user.get().getId());
                return new ResponseEntity<>("could not found user with given details.... user may not be verified", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
        /*    log.error(
                    "some error has occurred during fetching Users by id , in class UserService and its function getUserById ",
                    e.getMessage());*/
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("Unable to find Users, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * @param userName
     * @param password
     * @return user
     */
    public ResponseEntity<Object> getUserByNameAndPassword(String userName, String password) {
        try {
            Optional<Users> user = userRepository.findByUserNameAndPassword(userName, password);
            if (user.isPresent())
                return new ResponseEntity<>("login success", HttpStatus.OK);
            else
                return new ResponseEntity<>("incorrect login details, Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            log.error(
                    "some error has occurred during fetching Users by username , in class UserService and its function getUserByName ",
                    e.getMessage());
            return new ResponseEntity<>("Unable to Login either password or username might be incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param user
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> saveUser(Users user) {
        try {

            Boolean criminalRecord = feignPoliceRecordService.checkCriminalRecord("61101-7896541-5");

            System.out.println("record is from kamran :" + criminalRecord);
            Date date = DateTime.getDateTime();
            expirationTime = DateTime.getExpireTime();
            user.setCreatedDate(date);
            user.setActive(false);
            Random rndkey = new Random(); // Generating a random number
            int token = rndkey.nextInt(999999); // Generating a random email token of 6 digits
            user.setToken(token);
            // save user to db
            userRepository.save(user);
            user.toString();
            // send email token to user email and save in db
            emailUtil.sendMail(user.getEmail(), token);
            // send sms token to user email and save in db
            smsUtil.sendSMS(user.getPhoneNumber(), token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists  ", HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
           /* log.error(
                    "some error has occurred while trying to save user,, in class UserService and its function saveUser ",
                    e.getMessage());*/
            System.out.println("error is " + e.getMessage() + "  " + e.getCause());
            return new ResponseEntity<>("User could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param user
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> updateUser(Users user) {
        try {

            user.setUpdatedDate(DateTime.getDateTime());
            userRepository.save(user);
            user.toString();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
          /*  log.error(
                    "some error has occurred while trying to update user,, in class UserService and its function updateUser ",
                    e.getMessage());*/
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("User could not be added , Data maybe incorrect",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return
     * @author fawad khan
     * @createdDate 27-oct-2021
     */
    public ResponseEntity<Object> deleteUser(Long id) {
        try {
            Optional<Users> user = userRepository.findById(id);
            if (user.isPresent()) {
                // set status false
                user.get().setActive(false);
                // set updated date

                user.get().setUpdatedDate(DateTime.getDateTime());
                userRepository.save(user.get());
                return new ResponseEntity<>(" : Users deleted successfully", HttpStatus.OK);
            } else
                return new ResponseEntity<>(" : Users does not exists ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(
                    "some error has occurred while trying to Delete user,, in class UserService and its function deleteUser ",
                    e.getMessage(), e.getCause(), e);
            System.out.println("error is" + e.getCause() + " " + e.getMessage());

            return new ResponseEntity<>("Users could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * @param id
     * @param token
     * @return
     * @author fawad khan
     * @createdDate 14-oct-2021
     */
    public ResponseEntity<Object> verifyUser(Long id, int token) {
        try {
            Optional<Users> user = userRepository.findByIdAndToken(id, token);
            Date date = DateTime.getDateTime();
            if (user.isPresent()) {
                //check if token is not expired
                if (date.after(expirationTime)) {
                    System.out.println("token has expired");
                    return new ResponseEntity<>("Token verification time has expire please regenerate verification token ", HttpStatus.METHOD_NOT_ALLOWED);
                }
                System.out.println("user is : " + user.toString());
                user.get().setActive(true);
                userRepository.save(user.get());
                return new ResponseEntity<>("user has been verified ", HttpStatus.OK);
            } else
                return new ResponseEntity<>("incorrect verification details were entered or verification time has expired", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getCause());
            return new ResponseEntity<>("could not verify user please try again later",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * send token to user
     *
     * @param id
     * @return
     */
    public ResponseEntity<Object> sendToken(Long id) {
        try {
            Optional<Users> user = userRepository.findById(id);

            if (user.isPresent()) {
                expirationTime = DateTime.getExpireTime();
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
            /*log.error(
                    "some error has occurred during fetching User by id , in class UserService and its function sendSms ",
                    e.getMessage());*/
            System.out.println(e.getMessage() + e.getCause());
            return new ResponseEntity<>("Unable to find User, an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @param loan
     * @return
     */
    public ResponseEntity<Object> applyForLoan(Long id, Loans loan) {
        try {
            Optional<Users> user = userRepository.findById(id);
            if (user.isPresent() && user.get().isActive()) {
                // check if user is  verified
                // log.info("user fetch and found from db by id  : ", user.toString());
                loan.setDate(DateTime.getDateTime());
                loan.setInterestRate(interestRate);
                loan.setTotalAmountToBePaid(loan.getLoanAmount() + (interestRate * loan.getLoanAmount()));
                loan.setDueDate(DateTime.getDueDate());
                loan.setAmountPaid(0.0);
                loan.setPaidStatus(false);

                List<Loans> loans = new ArrayList<>(user.get().getLoans());
                loans.add(loan);
                user.get().setLoans(loans);
                userRepository.save(user.get());
                return new ResponseEntity<>(loan, HttpStatus.OK);
            } else
//                log.info("no user found with id:", user.get().getId());
                return new ResponseEntity<>("could not found user with given details.... user may not be verified", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Unable to approved loan, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public ResponseEntity<Object> depositLoan(Long id, Loans loan) {
        try {
            Optional<Users> user = userRepository.findById(id);
            Optional<Loans> depositLoan = loanRepository.findById(loan.getId());
            if (user.isPresent() && user.get().isActive() && depositLoan.isPresent()) {
                double paidAmount = loan.getAmountPaid() - depositLoan.get().getTotalAmountToBePaid();
                if (paidAmount > 0 || paidAmount < 0) {
                    return new ResponseEntity<>("paid amount is not equal to amount to be paid , please enter correct amount and try again", HttpStatus.FORBIDDEN);
                }
                // check if user is  verified
                // log.info("user fetch and found from db by id  : ", user.toString());
                loan.setDueDate(depositLoan.get().getDueDate());
                loan.setDate(depositLoan.get().getDate());
                if (Objects.equals(loan.getAmountPaid(), depositLoan.get().getTotalAmountToBePaid())) {
                    loan.setPaidStatus(true);
                }
                loan.setTotalAmountToBePaid(depositLoan.get().getTotalAmountToBePaid());
                loan.setAmountPaid(depositLoan.get().getAmountPaid());
                loan.setDescription(depositLoan.get().getDescription());
                loan.setDate(depositLoan.get().getDate());

                List<Loans> loansList = new ArrayList<>(user.get().getLoans());

                userRepository.save(user.get());
                return new ResponseEntity<>(loan, HttpStatus.OK);
            } else {
//                log.info("no user found with id:", user.get().getId());
                return new ResponseEntity<>("could not found any record with given details.... user may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Unable to approved loan, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    /**
     * @param id
     * @param funds
     * @return
     */
    public ResponseEntity<Object> applyForFunds(Long id, Funds funds) {
        try {
            Optional<Users> user = userRepository.findById(id);

            if (user.isPresent() && user.get().isActive()) {
                // check if user is  verified
                // log.info("user fetch and found from db by id  : ", user.toString());
                if (user.get().getOrganization().getType().equalsIgnoreCase("government")) {
                    List<Funds> fundsList = new ArrayList<>(user.get().getFunds());
                    user.get().setFunds(fundsList);
                    userRepository.save(user.get());
                } else
                    return new ResponseEntity<>("only government departments can apply for funds", HttpStatus.METHOD_NOT_ALLOWED);
                return new ResponseEntity<>(funds, HttpStatus.OK);
            } else
//                log.info("no user found with id:", user.get().getId());
                return new ResponseEntity<>("could not found user with given details.... user may not be verified", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Unable to approved loan, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


}