package light.novel.logger.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import light.novel.logger.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

	Set<Category> findAllByNameIn(Set<String> categories);
}
