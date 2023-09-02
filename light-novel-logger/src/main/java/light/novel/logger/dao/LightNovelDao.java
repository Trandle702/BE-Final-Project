package light.novel.logger.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import light.novel.logger.entity.LightNovel;

public interface LightNovelDao extends JpaRepository<LightNovel, Long> {
	
	Optional<LightNovel> findByVolumeNumber(Long volumeNumber);

}
