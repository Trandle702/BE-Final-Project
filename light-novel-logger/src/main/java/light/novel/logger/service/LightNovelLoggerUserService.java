package light.novel.logger.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import light.novel.logger.controller.model.UserData;
import light.novel.logger.dao.UserDao;
import light.novel.logger.entity.User;

@Service
public class LightNovelLoggerUserService {

	@Autowired
	private UserDao userDao;
	
	/*
	 * CRUD operations for the User class
	 */
	
	/**
	 * Method will either create a new User or update currently existing User with
	 * the userData passed in the parameter.
	 * 
	 * @param userData the info for the User to be saved/updated
	 * @return will return a DuplicateKeyException if email matches a User
	 * 			already in the database, otherwise will return a new/updated User
	 */
	@Transactional(readOnly = false)
	public UserData saveUser(UserData userData) {
		User user;
		
		if(Objects.isNull(userData.getUserId())) {
			Optional<User> opUser = userDao.findByEmail(userData.getEmail());
			
			if(opUser.isPresent()) {
				throw new DuplicateKeyException("User with email=" + userData.getEmail() + " already exists.");
			}
			
			
		}else {
			user = findUserById(userData.getUserId());
			
		}
		user = userData.toUser();
		
		
		return new UserData(userDao.save(user));
	}

	/**
	 * Method will attempt to find a User in the database using the userId passed in 
	 * the parameter.
	 * 
	 * @param userId the ID of the User to find
	 * @return will return NoSuchElementException if no User is found with the
	 * 		provided userId, otherwise will return the User 
	 */
	private User findUserById(Long userId) {
		return userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException(
						"User with ID=" + userId + " does not exists."));
	}

	/**
	 * Method will retrieve all Users from the database
	 * 
	 * @return will return a list of the Users in the database
	 */
	@Transactional(readOnly = true)
	public List<UserData> retrieveAllUsers() {
		List<User> users = userDao.findAll();
		List<UserData> response = new LinkedList<>();
		
		for(User user: users) {
			response.add(new UserData(user));
		}
		
		return response;
	}

	/**
	 * Method will attempt to retrieve a User by the userId passed in the
	 * parameter
	 * 
	 * @param userId the ID of the User that we are trying to retrieve
	 * @return will return the User if successful
	 */
	@Transactional(readOnly = true)
	public UserData retrieveUserById(Long userId) {
		User user = findUserById(userId);
		return new UserData(user);
	}

	/**
	 * Method will attempt to delete a User from the database
	 * 
	 * @param userId the ID of the User that we are trying to delete
	 */
	@Transactional(readOnly = false)
	public void deleteUserById(Long userId) {
		User user = findUserById(userId);
		userDao.delete(user);
	}
}
