package light.novel.logger.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import light.novel.logger.entity.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {

}
