package light.novel.logger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import light.novel.logger.controller.model.UserData;
import light.novel.logger.service.LightNovelLoggerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/light_novel_logger")
@Slf4j
public class LightNovelLoggerController {
	
	@Autowired
	private LightNovelLoggerService lightNovelLoggerService;
	
	@PostMapping("/user")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserData insertUser(@RequestBody UserData userData) {
		log.info("Creating new user {}", userData);
		return lightNovelLoggerService.saveUser(userData);
	}
	
//	@GetMapping("/user")
//	public List<UserData> retrieveAllUsers(){
//		log.info("Retrieving all users.");
//		return lightNovelLoggerService.retrieveAllUsers();
//	}
//	
	@GetMapping("/user/{userId}")
	public UserData retrieveUserById(@PathVariable Long userId) {
		log.info("Retrieving user with ID={}", userId);
		return lightNovelLoggerService.retrieveUserById(userId);
	}
}
