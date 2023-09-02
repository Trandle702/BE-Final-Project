package light.novel.logger.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import light.novel.logger.controller.model.UserData;
import light.novel.logger.service.LightNovelLoggerUserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/light_novel_logger")
@Slf4j
public class LightNovelLoggerUserController {
	
	@Autowired
	private LightNovelLoggerUserService userService;
	
	/*
	 * CRUD operations for the User Class - Create, Read, Update, Delete 
	 */
	
	/**
	 * Create CRUD operation - creates a new User
	 * 
	 * @param userData contains info to create new User
	 * @return will return a DuplicateKeyException if email matches a User
	 * 			already in the database, otherwise will return a new User
	 */
	@PostMapping("/user")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserData insertUser(@RequestBody UserData userData) {
		log.info("Creating new user {}", userData);
		return userService.saveUser(userData);
	}
	
	/**
	 * Read CRUD operation - retrieves all Users from database
	 * 
	 * @return will return a list of all the Users in the database
	 */
	@GetMapping("/user")
	public List<UserData> retrieveAllUsers(){
		log.info("Retrieving all users.");
		return userService.retrieveAllUsers();
	}
	
	/**
	 * Read CRUD operation - retrieves a User by ID
	 * 
	 * @param userId the ID of the User that is getting retrieve from the database
	 * @return will return NoSuchElementException if no User is found with the
	 * 		provided userId, otherwise will return User from database
	 */
	@GetMapping("/user/{userId}")
	public UserData retrieveUserById(@PathVariable Long userId) {
		log.info("Retrieving user with ID={}", userId);
		return userService.retrieveUserById(userId);
	}
	
	/**
	 * Update CRUD operation - updates the info of a User in the database
	 * 
	 * @param userId the ID of the User that will have their info updated
	 * @param userData contains the updated info for the User in the database
	 * @return will return NoSuchElementException if no User is found with the
	 * 		provided userId, otherwise will return the User with updated info
	 */
	@PutMapping("/user/{userId}")
	public UserData updateUser(@PathVariable Long userId, @RequestBody UserData userData) {
		userData.setUserId(userId);
		log.info("Updating user with ID={}", userId);
		return userService.saveUser(userData);
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/user")
	public void deleteAllUsers(){
		log.info("Attempting to delete all users");
		throw new UnsupportedOperationException("Deleting all users is not allowed");
	}
	
	/**
	 * Delete CRUD operation - will delete User from database
	 * 
	 * @param userId the ID of the User that will be deleted from the database
	 * @return will return NoSuchElementException if no User is found with the
	 * 		provided userId, otherwise will return a message saying that the
	 * 		deletion was successful
	 */
	@DeleteMapping("/user/{userId}")
	public Map<String, String> deleteUserById(@PathVariable Long userId){
		log.info("Deleting user with ID={}", userId);
		userService.deleteUserById(userId);
		return Map.of("message:", "Deletion of User with ID=" + userId + " was successful.");
	}
}
