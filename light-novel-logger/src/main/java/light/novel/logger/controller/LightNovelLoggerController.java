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
import light.novel.logger.controller.model.UserData.AuthorData;
import light.novel.logger.service.LightNovelLoggerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/light_novel_logger")
@Slf4j
public class LightNovelLoggerController {
	
	@Autowired
	private LightNovelLoggerService lightNovelLoggerService;
	
	/*
	 * CRUD operations for the User Class - Create, Read, Update, Delete 
	 */
	
	@PostMapping("/user")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserData insertUser(@RequestBody UserData userData) {
		log.info("Creating new user {}", userData);
		return lightNovelLoggerService.saveUser(userData);
	}
	
	@GetMapping("/user")
	public List<UserData> retrieveAllUsers(){
		log.info("Retrieving all users.");
		return lightNovelLoggerService.retrieveAllUsers();
	}
	
	@GetMapping("/user/{userId}")
	public UserData retrieveUserById(@PathVariable Long userId) {
		log.info("Retrieving user with ID={}", userId);
		return lightNovelLoggerService.retrieveUserById(userId);
	}
	
	@PutMapping("/user/{userId}")
	public UserData updateUser(@PathVariable Long userId, @RequestBody UserData userData) {
		userData.setUserId(userId);
		log.info("Updating user with ID={}", userId);
		return lightNovelLoggerService.saveUser(userData);
	}
	
	@DeleteMapping("/user")
	public void deleteAllUsers(){
		log.info("Attempting to delete all users");
		throw new UnsupportedOperationException("Deleting all contributors is not allowed");
	}
	
	@DeleteMapping("/user/{userId}")
	public Map<String, String> deleteUserById(@PathVariable Long userId){
		log.info("Deleting user with ID={}", userId);
		lightNovelLoggerService.deleteUserById(userId);
		return Map.of("message:", "Deletion of User with ID=" + userId + " was successful.");
	}
	
	/*
	 * Crud operations for the Author class - Create, Read, Update
	 */
	
	@PostMapping("/author")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AuthorData insertAuthor(@RequestBody AuthorData authorData) {
		log.info("Creating new author {}", authorData);
		return lightNovelLoggerService.saveAuthor(authorData);
	}
	
	@GetMapping("/author")
	public List<AuthorData> retrieveAllAuthors(){
		log.info("Retrieving all authors.");
		return lightNovelLoggerService.retrieveAllAuthors();
	}
}
