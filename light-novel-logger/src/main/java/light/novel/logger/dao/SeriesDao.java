package light.novel.logger.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import light.novel.logger.entity.Series;

public interface SeriesDao extends JpaRepository<Series, Long> {

	Optional<Series> findByName(String seriesName);
}
