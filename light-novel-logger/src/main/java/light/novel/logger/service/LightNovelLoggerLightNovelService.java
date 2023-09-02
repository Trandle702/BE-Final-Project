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

import light.novel.logger.controller.model.LightNovelData;
import light.novel.logger.dao.LightNovelDao;
import light.novel.logger.dao.SeriesDao;
import light.novel.logger.dao.UserDao;
import light.novel.logger.entity.LightNovel;
import light.novel.logger.entity.Series;
import light.novel.logger.entity.User;

@Service
public class LightNovelLoggerLightNovelService {

	@Autowired
	UserDao userDao;

	@Autowired
	SeriesDao seriesDao;

	@Autowired
	LightNovelDao lightNovelDao;

	/**
	 * Method will create a new LightNovel.
	 * 
	 * @param lightNovelData the info for the LightNovel to be saved
	 * @return will return a new Author
	 */
	@Transactional(readOnly = false)
	public LightNovelData saveLightNovel(Long userId, Long seriesId, LightNovelData lightNovelData) {
		User user = findUserById(userId);
		Series series = findSeriesById(seriesId);
		LightNovel lightNovel = findOrCreateLightNovel(lightNovelData.getLightNovelId(), seriesId, lightNovelData.getVolumeNumber());

		lightNovel = lightNovelData.toLightNovel();
		series.getVolumes().add(lightNovel);
		lightNovel.setSeries(series);
		user.getSeries().add(series);

		return new LightNovelData(lightNovelDao.save(lightNovel));
	}

	/**
	 * Method will update currently existing LightNovel with
	 * the lightNovelData passed in the parameter.
	 * 
	 * @param lightNovelData the info for the LightNovel to be updated
	 * @return will return a updated LightNovel
	 */
	@Transactional(readOnly = false)
	public LightNovelData updateLightNovel(Long userId, Long seriesId, LightNovelData lightNovelData) {
		List<LightNovel> curLightNovels = lightNovelDao.findAll();
		Optional<LightNovel> opCurLightNovel = lightNovelDao.findByVolumeNumber(lightNovelData.getVolumeNumber());

		for (LightNovel curLightNovel : curLightNovels) {
			if (opCurLightNovel.isPresent() && 
					(curLightNovel.getLightNovelId().equals(lightNovelData.getLightNovelId())) &&
					!(curLightNovel.getVolumeNumber().equals(lightNovelData.getVolumeNumber()))) {
				throw new DuplicateKeyException("Light novel with volume number=" + lightNovelData.getVolumeNumber() + " already exists.");
			}
		}
		
		Series series = findSeriesById(seriesId);
		LightNovel lightNovel = findLightNovelById(lightNovelData.getLightNovelId());
		Optional<LightNovel> opLightNovel = lightNovelDao.findById(lightNovelData.getLightNovelId());

		if (opLightNovel.isPresent()) {
			opLightNovel.get().setSeries(series);
			opLightNovel.get().setVolumeNumber(lightNovelData.getVolumeNumber());
			opLightNovel.get().setPageCount(lightNovelData.getPageCount());
			opLightNovel.get().setDescription(lightNovelData.getDescription());

			return new LightNovelData(lightNovelDao.save(opLightNovel.get()));
		}

		return new LightNovelData(lightNovel);
	}

	/**
	 * Method will check to see if a LightNovel can be created
	 * 
	 * @param lightNovelId the ID of the LightNovel that we are attempting to create
	 * @param seriesId the ID of the Series that we are creating the LightNovel for
	 * @param volumeNumber the order number of the LightNovel in the Series
	 * @return will return a DuplicateKeyException if there already is a LightNovel with the 
	 * 			same volumeNumber passed in the parameter, otherwise will return a LightNovel
	 */
	private LightNovel findOrCreateLightNovel(Long lightNovelId, Long seriesId, Long volumeNumber) {
		LightNovel lightNovel;

		if (Objects.isNull(lightNovelId)) {
			List<LightNovel> curLightNovels = lightNovelDao.findAll();
			Optional<LightNovel> opLightNovel = lightNovelDao.findByVolumeNumber(volumeNumber);

			for (LightNovel curLightNovel : curLightNovels) {
				if (opLightNovel.isPresent() && curLightNovel.getSeries().getSeriesId().equals(seriesId)) {
					throw new DuplicateKeyException("Light novel with volume number=" + volumeNumber + " already exists.");
				}
			}
			lightNovel = new LightNovel();
		} else {
			lightNovel = findLightNovelById(lightNovelId);
		}

		return lightNovel;
	}

	/**
	 * Method will attempt to find a LightNovel from the selected Series using the lightNovelId passed
	 * in the parameter
	 * 
	 * @param lightNovelId the ID of the LightNovel to find
	 * @return will return NoSuchElementException if no LightNovel is found with the
	 * 		provided lightNovelId, otherwise will return the LightNovel
	 */
	private LightNovel findLightNovelById(Long lightNovelId) {
		return lightNovelDao.findById(lightNovelId).orElseThrow(
				() -> new NoSuchElementException("Light Novel with ID=" + lightNovelId + " does not exists."));
	}

	/**
	 * Method will attempt to find a Series from the selected User using the seriesId passed
	 * in the parameter
	 * 
	 * @param seriesId the ID of the Series to find
	 * @return will return NoSuchElementException if no Series is found with the
	 * 		provided seriesId, otherwise will return the Series
	 */
	private Series findSeriesById(Long seriesId) {
		return seriesDao.findById(seriesId)
				.orElseThrow(() -> new NoSuchElementException("Series with ID=" + seriesId + " does not exists."));
	}

	/**
	 * Method will attempt to find a User in the database using the UserId passed in
	 * the parameter
	 * 
	 * @param userId the ID of the User to find
	 * @return will return NoSuchElementException if no User is found with the
	 * 		provided userId, otherwise will return the User
	 */
	private User findUserById(Long userId) {
		return userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User with ID=" + userId + " does not exists."));
	}

	/**
	 * Method will retrieve all LightNovels from the database
	 * 
	 * @return will return a list of the LightNovels in the database
	 */
	@Transactional(readOnly = true)
	public List<LightNovelData> retrieveAllLightNovels() {
		List<LightNovel> lightNovels = lightNovelDao.findAll();
		List<LightNovelData> response = new LinkedList<>();

		for (LightNovel lightNovel : lightNovels) {
			response.add(new LightNovelData(lightNovel));
		}

		return response;
	}

	/**
	 * Method will attempt to retrieve a LightNovel by the lightNovelId passed in the
	 * parameter
	 * 
	 * @param lightNovelId the ID of the LightNovel that we are trying to retrieve
	 * @return will return the LightNovel if successful
	 */
	@Transactional(readOnly = true)
	public LightNovelData retrieveLightNovelById(Long lightNovelId) {
		LightNovel lightNovel = findLightNovelById(lightNovelId);
		return new LightNovelData(lightNovel);
	}
}
