package light.novel.logger.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import light.novel.logger.entity.User;

public interface UserDao extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String userEmail);

}
