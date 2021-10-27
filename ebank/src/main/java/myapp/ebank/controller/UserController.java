package myapp.ebank.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import myapp.ebank.model.Users;
import myapp.ebank.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	UserService userService;
	private static final String defaultAuthValue = "user12345";

	private static final Logger log = LogManager.getLogger();

	public UserController(UserService userService) {
		this.userService = userService;
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
	 * @Author "Fawad"
	 * @Description "Login it takes username and password from frontend then check
	 *              from database by calling object with email"
	 * @param userName
	 * @param password
	 * @createdDate 27-oct-2021
	 * @return
	 */
	@GetMapping("/login")
	public ResponseEntity<Object> login(@RequestParam(value = "username") String userName,
			@RequestParam(value = "password") String password) {
		return userService.getUserByNameAndPassword(userName, password);
	}

	/**
	 * @Author "Fawad khan"
	 * @Description "Display all user from db in a list if present which can be then
	 *              displayed on screen"
	 * @param authValue
	 * @createdDate 27-oct-2021
	 * @return list of users
	 */

	@GetMapping("/all")
	public ResponseEntity<Object> users(@RequestHeader(value = "Authorization", required = false) String authValue) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.listAllUser();
			} else
				return new ResponseEntity<>(" Not authorize", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * @author Fawad khan
	 * @param authValue
	 * @param user
	 * @createdDate 27-oct-2021
	 * @return added user object
	 */
	@PostMapping("/add")
	public ResponseEntity<Object> addUser(@RequestHeader(value = "Authorization", required = false) String authValue,
			@RequestBody Users user) {
		// check authorization
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.saveUser(user);
			} else
				return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * 
	 * @param userid
	 * @param emailToken
	 * @param smsToken
	 * @createdDate 14-oct-2021
	 * @return String of User verified or not
	 */
	@GetMapping("/verify")
	public ResponseEntity<Object> verifyUser(@RequestHeader(value = "id") Long userid,
			@RequestHeader(value = "emailToken") int emailToken, @RequestHeader(value = "smsToken") int smsToken) {

		return userService.verifyUser(userid, emailToken, smsToken);

	}

	

	/**
	 * 
	 * @param authValue
	 * @param id
	 * @createdDate 27-oct-2021
	 * @return user object
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
	 *
	 * @param authValue
	 * @param id
	 * @param message
	 * @createdDate 13-oct-2021
	 */
	@PostMapping("/{id}/sendSms")
	public ResponseEntity<Object> sendSms(@RequestHeader(value = "Authorization", required = false) String authValue,
			@PathVariable Long id, @RequestBody String message) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.sendSms(id, message);
			} else {
				return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @param authValue
	 * @param user
	 * @createdDate 27-oct-2021
	 * @return
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
	 * 
	 * @param authValue
	 * @param id
	 * @createdDate 27-oct-2021
	 * @return
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteUser(@RequestHeader(value = "Authorization", required = false) String authValue,
			@PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.deleteUser(id);
			} else
				return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

}