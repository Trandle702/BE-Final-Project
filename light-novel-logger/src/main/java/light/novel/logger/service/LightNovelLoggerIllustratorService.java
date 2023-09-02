package light.novel.logger.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import light.novel.logger.controller.model.IllustratorData;
import light.novel.logger.dao.IllustratorDao;
import light.novel.logger.dao.SeriesDao;
import light.novel.logger.dao.UserDao;
import light.novel.logger.entity.Illustrator;
import light.novel.logger.entity.Series;
import light.novel.logger.entity.User;

@Service
public class LightNovelLoggerIllustratorService {

	@Autowired
	private IllustratorDao illustratorDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SeriesDao seriesDao;
	
	/**
	 * Method will either create a new Illustrator or update currently existing Illustrator with
	 * the illustratorData passed in the parameter.
	 * 
	 * @param illustratorData the info for the Illustrator to be saved/updated
	 * @return will return a new/updated Illustrator
	 */
	@Transactional(readOnly = false)
	public IllustratorData saveIllustrator(Long userId, Long seriesId, IllustratorData illustratorData) {
		User user = findUserById(userId);
		Series series = findSeriesById(seriesId);
		Illustrator illustrator = illustratorData.toIllustrator();
		List<Illustrator> illustrators = illustratorDao.findAll();
		
		for(Illustrator daoIllustrator : illustrators) {
			if(daoIllustrator.getFirstName().equals(illustrator.getFirstName()) &&
			   daoIllustrator.getLastName().equals(illustrator.getLastName())) {
				daoIllustrator.getSeries().add(series);
				series.setIllustrator(daoIllustrator);
				user.getSeries().add(series);
				return new IllustratorData(illustratorDao.save(daoIllustrator));
			}
		}
		
		illustrator.getSeries().add(series);
		series.setIllustrator(illustrator);
		user.getSeries().add(series);
		
		return new IllustratorData(illustratorDao.save(illustrator));
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
				.orElseThrow(() -> new NoSuchElementException("user with ID=" + userId + " does not exists."));
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
				.orElseThrow(() -> new NoSuchElementException("series with ID=" + seriesId + " does not exists."));
	}
	
	/**
	 * Method will retrieve all Illustrators from the database
	 * 
	 * @return will return a list of the Illustrators in the database
	 */
	@Transactional(readOnly = true)
	public List<IllustratorData> retrieveAllIllustrators() {
		List<Illustrator> illustrators = illustratorDao.findAll();
		List<IllustratorData> response = new LinkedList<>();
		
		for(Illustrator illustrator : illustrators) {
			response.add(new IllustratorData(illustrator));
		}
		
		return response;
	}

	/**
	 * Method will attempt to retrieve a Illustrator by the illustratorId passed in the
	 * parameter
	 * 
	 * @param illustratorId the ID of the Illustrator that we are trying to retrieve
	 * @return will return the Illustrator if successful
	 */
	@Transactional(readOnly = true)
	public IllustratorData retrieveIllustratorById(Long illustratorId) {
		Illustrator illustrator = findIllustratorById(illustratorId);
		return new IllustratorData(illustrator);
	}

	/**
	 * Method will attempt to find a Illustrator in the database using the illustratorId passed in 
	 * the parameter.
	 * 
	 * @param illustratorId the ID of the Illustrator to find
	 * @return will return NoSuchElementException if no Illustrator is found with the
	 * 		provided illustratorId, otherwise will return the Illustrator 
	 */
	private Illustrator findIllustratorById(Long illustratorId) {
		return illustratorDao.findById(illustratorId)
				.orElseThrow(() -> new NoSuchElementException(
						"Illustrator with ID=" + illustratorId + " does not exists."));
	}
}
