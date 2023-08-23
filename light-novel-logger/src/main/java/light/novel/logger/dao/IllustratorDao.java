package light.novel.logger.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import light.novel.logger.entity.Illustrator;

public interface IllustratorDao extends JpaRepository<Illustrator, Long> {

}
