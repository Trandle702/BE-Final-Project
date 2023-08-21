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
public class LightNovelLoggerService {
	
	@Autowired
	private UserDao userDao;

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

	private User findUserById(Long userId) {
		return userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException(
						"User with ID=" + userId + " does not exists."));
	}

	@Transactional(readOnly = true)
	public List<UserData> retrieveAllUsers() {
		List<User> users = userDao.findAll();
		List<UserData> response = new LinkedList<>();
		
		for(User user: users) {
			response.add(new UserData(user));
		}
		
		return response;
	}

	@Transactional(readOnly = true)
	public UserData retrieveUserById(Long userId) {
		User user = findUserById(userId);
		return new UserData(user);
	}

	@Transactional(readOnly = false)
	public void deleteUserById(Long userId) {
		User user = findUserById(userId);
		userDao.delete(user);
	}

}
