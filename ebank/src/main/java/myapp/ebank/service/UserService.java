package myapp.ebank.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import myapp.ebank.model.Users;
import myapp.ebank.repository.UserRepository;
import myapp.ebank.util.DateAndTime;
import myapp.ebank.util.EmailUtil;
import myapp.ebank.util.SMSUtil;

/**
 * @author Fawad khan Created Date : 08-October-2021 A service class of user
 *         connected with repository which contains user CRUD operations
 * @createdDate 27-oct-2021
 */
@Service
public class UserService {
	final private UserRepository userRepository;
	final private EmailUtil emailUtil;
	final private RestTemplate restTemplate = new RestTemplate();
	final private SMSUtil smsUtil = new SMSUtil();

	private static final Logger log = LogManager.getLogger(UserService.class);

	// Autowiring through constructor
	public UserService(UserRepository userRepository, EmailUtil emailUtil) {
		this.userRepository = userRepository;
		this.emailUtil = emailUtil;
	}

	/**
	 * @author Fawad khan
	 * @return List of users
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
			return new ResponseEntity<>("Users could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author fawad khan
	 * @createdDate 27-oct-2021
	 * @param id
	 * @return
	 */
	// get user by specific id
	public ResponseEntity<Object> getUserById(Long id) {
		try {
			Optional<Users> user = userRepository.findById(id);
			if (user.isPresent()) {
				log.info("user fetch and found from db by id  : ", user.toString());
				return new ResponseEntity<>(user, HttpStatus.FOUND);
			} else
				log.info("no user found with id:", user.get().getId());
			return new ResponseEntity<>("could not found user with given details....", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching Users by id , in class UserService and its function getUserById ",
					e.getMessage());

			return new ResponseEntity<>("Unable to find Users, an error has occurred",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/*
	 * // send user sms public ResponseEntity<Object> sendSms(Long id, String
	 * message) { try { Optional<Users> user = userRepository.findById(id); if
	 * (user.isPresent()) { log.info("user fetch and found from db by id  : ",
	 * user.toString()); return smsUtil.sendSMS(user.get().getPhoneNumber(),
	 * message); } } catch (Exception e) { log.error(
	 * "some error has occurred during fetching Users by id , in class UserService and its function sendSms "
	 * , e.getMessage()); System.out.println(e.getMessage() + e.getCause()); return
	 * new ResponseEntity<>("Unable to find Users, an error has occurred",
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	/**
	 *
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
			log.error(
					"some error has occurred during fetching Users by username , in class UserService and its function getUserByName ",
					e.getMessage());
			return new ResponseEntity<>("Unable to Login either password or username might be incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author fawad khan
	 * @createdDate 27-oct-2021
	 * @param user
	 * @return
	 */
	public ResponseEntity<Object> saveUser(Users user) {
		try {

			String date = DateAndTime.getDate();
			user.setCreatedDate(date);
			user.setActive(true);
			// save user to db
			userRepository.save(user);
			user.toString();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(" Data already exists .. duplicates not allowed ", HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to save user,, in class UserService and its function saveUser ",
					e.getMessage());
			System.out.println("error is " + e.getMessage() + "  " + e.getCause());
			return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author fawad khan
	 * @createdDate 27-oct-2021
	 * @param user
	 * @return
	 */
	public ResponseEntity<Object> updateUser(Users user) {
		try {
			String pattern = "dd-MM-yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			user.setUpdatedDate(date);
			userRepository.save(user);
			user.toString();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "  " + e.getCause());
			log.error(
					"some error has occurred while trying to update user,, in class UserService and its function updateUser ",
					e.getMessage());
			return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @author fawad khan
	 * @createdDate 27-oct-2021
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> deleteUser(Long id) {
		try {
			Optional<Users> user = userRepository.findById(id);
			if (user.isPresent()) {

				// set status false
				user.get().setActive(false);
				// set updated date
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				user.get().setUpdatedDate(date);
				userRepository.save(user.get());
				return new ResponseEntity<>("SMS: Users deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("SMS: Users does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to Delete user,, in class UserService and its function deleteUser ",
					e.getMessage(), e.getCause(), e);
			return new ResponseEntity<>("Users could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/**
	 * @author fawad khan
	 * @createdDate 14-oct-2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */
	public ResponseEntity<Object> verifyUser(Long id, int emailToken, int smsToken) {
		try {
			Optional<Users> user = userRepository.findByIdAndEmailTokenAndSmsToken(id, emailToken, smsToken);
			if (user.isPresent()) {
				System.out.println("user is : " + user.toString());
				user.get().setActive(true);
				userRepository.save(user.get());
				return new ResponseEntity<>("user has been verified ", HttpStatus.OK);
			} else
				return new ResponseEntity<>("incorrect verification details were entered", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			System.out.println(e.getCause());
			return new ResponseEntity<>("could not verify user please try again later",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}