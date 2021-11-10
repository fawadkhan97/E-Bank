package myapp.ebank.service;

import myapp.ebank.model.dto.UserFundsAndLoans;
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
import org.hibernate.PropertyValueException;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author Fawad khan Created Date : 08-October-2021 A service class of user
 * connected with repository which contains user CRUD operations
 * createdDate 27-oct-2021
 */
@Service
public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    final LoanRepository loanRepository;
    final FeignPoliceRecordService feignPoliceRecordService;
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;
    private final SMSUtil smsUtil = new SMSUtil();
    private final Double interestRate = 0.045;
    private final Double loanAmount = 5000000.0;
    private final Double fundAmount = 10000000.0;
    int unpaidLoanCount = 0;
    private Date expirationTime;


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
    public ResponseEntity<Object> listAllUser() {
        try {
            List<Users> users = userRepository.findAllByActiveOrderByCreatedDateDesc(true);
            // check if list is empty
            if (users.isEmpty()) {
                return new ResponseEntity<>("  Users are empty", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }

        } catch (Exception e) {
            log.info("some error has occurred trying to Fetch users, in Class  UserService and its function listAllUser ");
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
    public ResponseEntity<Object> getUserById(Long id) {
        try {
            Optional<Users> user = userRepository.findByIdAndActive(id,true);
            if (user.isPresent()) {
                // check if user is verified
                log.info("user fetch and found from db by id  : ", user.toString());
                return new ResponseEntity<>(user, HttpStatus.FOUND);
            } else {
                log.info("no user found with id:", user.get().getId());
                return new ResponseEntity<>("could not found user with given details.... user may not be verified", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.debug(
                    "some error has occurred during fetching Users by id , in class UserService and its function getUserById ",
                    e.getMessage());
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
            if (user.isPresent() && user.get().isActive())
                return new ResponseEntity<>("login success", HttpStatus.OK);
            else
                return new ResponseEntity<>("incorrect login details, Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            log.debug(
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
    public ResponseEntity<Object> saveUser(Users user) throws HttpMessageNotReadableException {
        try {
            //   Boolean criminalRecord = feignPoliceRecordService.checkCriminalRecord("61101-7896541-5");
            //   System.out.println("record is from kamran :" + criminalRecord);
            Date date = DateTime.getDateTime();
            expirationTime = DateTime.getExpireTime(2);
            user.setCreatedDate(date);
            user.setUpdatedDate(null);
            user.setActive(false);
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
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidDataAccessApiUsageException | TransientPropertyValueException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("organization with given id does not exist or organization is not provided", HttpStatus.NOT_ACCEPTABLE);
        } catch (PropertyValueException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("some required fields might be null", HttpStatus.NOT_ACCEPTABLE);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>(" Some Data field maybe missing or Data already exists   ", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            log.debug(
                    "some error has occurred while trying to save user,, in class UserService and its function saveUser ",
                    e.getMessage());
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
            Optional<Users> user1 = userRepository.findById(user.getId());
            if (user1.isPresent()) {
                user.setUpdatedDate(DateTime.getDateTime());
                user.setCreatedDate(user1.get().getCreatedDate());
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else return new ResponseEntity<>("user is not present id might be in correct", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            System.out.println(e.getMessage() + "  " + e.getCause());
            log.debug(
                    "some error has occurred while trying to update user,, in class UserService and its function updateUser ",
                    e.getMessage());
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
            Optional<Users> user = userRepository.findByIdAndActive(id,true);
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
            log.debug(
                    "some error has occurred while trying to Delete user, in class UserService and its function deleteUser ",
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
            /*log.debug(
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
            if (loan.getAmountPaid() < loanAmount) {
                Optional<Users> user = userRepository.findById(id);
                if (user.isPresent() && user.get().isActive()) {
                    List<Loans> loans = new ArrayList<>(user.get().getLoans());
                    if (!loans.isEmpty()) {
                        for (Loans loan1 : loans) {
                            if (!loan1.getPaidStatus()) {
                                unpaidLoanCount += 1;
                            }
                        }
                        if (unpaidLoanCount > 2) {
                            return new ResponseEntity<>("user has already pending unpaid loans", HttpStatus.METHOD_NOT_ALLOWED);
                        }
                    }
                    // log.info("user fetch and found from db by id  : ", user.toString());
                    loan.setCreatedDate(DateTime.getDateTime());
                    loan.setInterestRate(interestRate);
                    loan.setTotalAmountToBePaid(loan.getLoanAmount() + (interestRate * loan.getLoanAmount()));
                    loan.setDueDate(DateTime.getDueDate(5));
                    loan.setAmountPaid(0.0);
                    loan.setPaidStatus(false);
                    loan.setPaidDate(null);

                    loans.add(loan);
                    user.get().setLoans(loans);
                    // save loan to db first then save user
                    loanRepository.save(loan);
                    userRepository.save(user.get());
                    return new ResponseEntity<>(loan, HttpStatus.OK);
                } else
//                log.info("no user found with id:", user.get().getId());
                    return new ResponseEntity<>("could not found user with given details.... user may not be verified", HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity<>("maximum allowed limit of loan is {loanAMount} ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.info("an error has occurred in userService method apply for loan", e.getMessage());
            System.out.println("error is : " + e.getCause() + "  " + e.getMessage());
            return new ResponseEntity<>("Unable to approved loan, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @param loan
     * @return
     */
    public ResponseEntity<Object> depositLoan(Long id, Loans loan) {

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
            log.info("an error has occured in userService method depositLoan", e.getMessage());
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Unable to deposit loan, an error has occurred",
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
            if (user.isPresent() && user.get().isActive() && user.get().getOrganization().getType().equalsIgnoreCase("government")) {
                // check if user is  verified
                List<Funds> fundsList = new ArrayList<>(user.get().getFunds());
                if (funds.getAmount() > fundAmount) {
                    funds.setCreatedDate(DateTime.getDateTime());
                    funds.setApprovedStatus(false);
                    fundsList.add(funds);
                    user.get().setFunds(fundsList);
                    userRepository.save(user.get());
                    return new ResponseEntity<>("Funds requested has been received..it will be process and its status will be updated soon", HttpStatus.OK);
                }
                funds.setCreatedDate(DateTime.getDateTime());
                funds.setApprovedStatus(true);
                fundsList.add(funds);
                user.get().setFunds(fundsList);
                userRepository.save(user.get());
                return new ResponseEntity<>("funds has been approved ", HttpStatus.OK);
            } else if (!user.get().getOrganization().getType().equalsIgnoreCase("government")) {
                return new ResponseEntity<>("only government departments can apply for funds", HttpStatus.METHOD_NOT_ALLOWED);
            } else
                return new ResponseEntity<>("could not found user with given details.... user may not be verified", HttpStatus.NOT_FOUND);
        } catch (
                Exception e) {
            System.out.println("error is" + e.getCause() + " " + e.getMessage());
            return new ResponseEntity<>("Unable to approved loan, an error has occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param id
     * @return
     */
    public ResponseEntity<Object> getUserFundsAndLoans(Long id) {
        try {
            Optional<Users> user = userRepository.findByIdAndActive(id, true);
            if (user.isPresent()) {
                // save Funds and Loans from user object
                UserFundsAndLoans userFundsAndLoans = new UserFundsAndLoans();
                userFundsAndLoans.setFunds(user.get().getFunds());
                userFundsAndLoans.setLoans(user.get().getLoans());
                return new ResponseEntity<>(userFundsAndLoans, HttpStatus.OK);
            } else return new ResponseEntity<>("user may not exists for given id", HttpStatus.NOT_FOUND);


        } catch (Exception e) {
            log.debug(
                    "some error has occurred during fetching User by id , in class UserService and its function getUserFundsAndLoans ",
                    e.getMessage());
            System.out.println(e.getMessage() + " " + e.getCause());
            return new ResponseEntity<>("Unable to find User, an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}