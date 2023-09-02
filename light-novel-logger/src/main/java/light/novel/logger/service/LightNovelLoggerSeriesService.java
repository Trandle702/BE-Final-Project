package light.novel.logger.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import light.novel.logger.controller.model.SeriesData;
import light.novel.logger.dao.AuthorDao;
import light.novel.logger.dao.CategoryDao;
import light.novel.logger.dao.IllustratorDao;
import light.novel.logger.dao.SeriesDao;
import light.novel.logger.dao.UserDao;
import light.novel.logger.entity.Author;
import light.novel.logger.entity.Category;
import light.novel.logger.entity.Illustrator;
import light.novel.logger.entity.Series;
import light.novel.logger.entity.User;

@Service
public class LightNovelLoggerSeriesService {

	@Autowired
	private SeriesDao seriesDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private IllustratorDao illustratorDao;

	/**
	 * Method will create a new Series with the seriesData passed in the parameter
	 * 
	 * @param userId the ID of the User that we are creating a Series to                   
	 * @param seriesData the info for the Series to be saved
	 * @return will return an IllegalArgumentException if LightNovelData is passed in with
	 * 		   the LightNovelData, otherwise will return a new Series
	 */
	@Transactional(readOnly = false)
	public SeriesData saveSeries(Long userId, SeriesData seriesData) {
		if (!seriesData.getVolumes().isEmpty()) {
			throw new IllegalArgumentException(
					"Please create series before you attempt to add light novel(s) to series.");
		}

		User user = findUserById(userId);
		Series series = findOrCreateSeries(seriesData.getSeriesId(), seriesData.getName());
		Set<Category> categories = findCategories(seriesData);

		series = seriesData.toSeries();
		series.setCategories(categories);
		user.getSeries().add(series);
		series.setUser(user);

		return new SeriesData(seriesDao.save(series));
	}

	/**
	 * Method will update a currently existing Series with the seriesData passed
	 * in the parameter 
	 * 
	 * @param userId the ID of the User that owns the Series that we are updating
	 * @param seriesData the info for the Series to be updated
	 * @return will return an IllegalArgumentException if LightNovelData is passed in with
	 * 		   the LightNovelData, otherwise will return a updated Series
	 */
	@Transactional(readOnly = false)
	public SeriesData updateSeries(Long userId, SeriesData seriesData) {
		if (!seriesData.getVolumes().isEmpty()) {
			throw new IllegalArgumentException("Cannot create new light novel(s) when updating series.");
		}

		User user = findUserById(userId);
		Series series = findSeriesById(seriesData.getSeriesId());
		Optional<Series> opSeries = seriesDao.findById(seriesData.getSeriesId());

		if (opSeries.isPresent()) {
			opSeries.get().setUser(user);
			opSeries.get().setName(seriesData.getName());
			opSeries.get().setCategories(findCategories(seriesData));

			return new SeriesData(seriesDao.save(opSeries.get()));
		}

		return new SeriesData(series);
	}

	/**
	 * Method will attempting to create a Set of Categories based on the Category info
	 * found in seriesData
	 * 
	 * @param seriesData the info for the Series that may/may not contain Categories
	 * @return will return a Set of Categories
	 */
	private Set<Category> findCategories(SeriesData seriesData) {
		Long seriesId;
		Series series;
		Set<Category> response;
		Set<String> strCategories = new HashSet<>();

		if (Objects.isNull(seriesData.getCategories())) {
			if (Objects.isNull(seriesData.getSeriesId())) {
				response = new HashSet<>();
			} else {
				seriesId = seriesData.getSeriesId();
				series = findSeriesById(seriesId);
				response = series.getCategories();
			}
		} else {
			if (Objects.isNull(seriesData.getSeriesId())) {
				for (Category category : seriesData.toSeries().getCategories()) {
					strCategories.add(category.getName());
				}

				response = categoryDao.findAllByNameIn(strCategories);
			} else {
				seriesId = seriesData.getSeriesId();
				series = findSeriesById(seriesId);
				Set<Category> preCategories = series.getCategories();

				for (Category category : seriesData.toSeries().getCategories()) {
					strCategories.add(category.getName());
				}

				for (Category category : preCategories) {
					strCategories.add(category.getName());
				}

				response = categoryDao.findAllByNameIn(strCategories);
			}
		}
		return response;
	}

	/**
	 * Method will check to see if a Series can be created
	 * 
	 * @param seriesId the ID of the Series that we are attempting to create
	 * @param seriesName the name of the Series
	 * @return will return a DuplicateKeyException if there already is a Series with
	 * 			the same name passed in the parameter, otherwise will return a Series
	 */
	private Series findOrCreateSeries(Long seriesId, String seriesName) {
		Series curSeries;
		Optional<Series> opSeries = seriesDao.findByName(seriesName);

		if (Objects.isNull(seriesId)) {
			if (opSeries.isPresent()) {
				throw new DuplicateKeyException("Series with name=" + seriesName + " already exists.");
			}
			curSeries = new Series();
			curSeries.setAuthor(new Author());
			curSeries.setIllustrator(new Illustrator());
		} else {
			List<Series> currentSeries = seriesDao.findAll();
			for (Series series : currentSeries) {
				if (series.getName().equals(seriesName) && (!series.getSeriesId().equals(seriesId))) {
					throw new DuplicateKeyException("Series with name=" + seriesName + " already exists.");
				}
			}
			curSeries = findSeriesById(seriesId);
		}

		return curSeries;
	}

	/**
	 * Method will attempt to find a User in the database using the UserId passed in
	 * the parameter
	 * 
	 * @param userId the ID of the User to find
	 * @return will return NoSuchElementException if no User is found with the
	 *         provided userId, otherwise will return the User
	 */
	private User findUserById(Long userId) {
		return userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("user with ID=" + userId + " does not exists."));
	}

	/**
	 * Method will attempt to find an Author in the database using the authorId passed in
	 * the parameter
	 * 
	 * @param authorId the ID of the Author to find
	 * @return will return NoSuchElementException if no Author is found with the
	 *         provided authorId, otherwise will return the Author
	 */
	private Author findAuthorById(Long authorId) {
		return authorDao.findById(authorId)
				.orElseThrow(() -> new NoSuchElementException("user with ID=" + authorId + " does not exists."));
	}
	
	/**
	 * Method will attempt to find a Illustrator in the database using the illustratorId passed in
	 * the parameter
	 * 
	 * @param illustratorId the ID of the illustrator to find
	 * @return will return NoSuchElementException if no Illustrator is found with the
	 *         provided illustratorId, otherwise will return the Illustrator
	 */
	private Illustrator findIllustratorById(Long illustratorId) {
		return illustratorDao.findById(illustratorId)
				.orElseThrow(() -> new NoSuchElementException("user with ID=" + illustratorId + " does not exists."));
	}

	/**
	 * Method will retrieve all the Series that the selected User has
	 * 
	 * @return will return a list of the Series that the User has
	 */
	@Transactional(readOnly = true)
	public List<SeriesData> retrieveAllSeries(Long userId) {
		List<Series> dbSeries = seriesDao.findAll();
		List<SeriesData> response = new LinkedList<>();

		for (Series series : dbSeries) {
			if (series.getUser().getUserId().equals(userId)) {
				response.add(new SeriesData(series));
			}
		}

		return response;
	}

	/**
	 * Method will attempt to retrieve a Series by the seriesId passed in the
	 * parameter
	 * 
	 * @param seriesId the ID of the Series that we are trying to retrieve
	 * @return will return the Author if successful
	 */
	@Transactional(readOnly = true)
	public SeriesData retrieveSeriesById(Long seriesId) {
		Series series = findSeriesById(seriesId);
		return new SeriesData(series);
	}

	/**
	 * Method will attempt to find a Series from the selected User using the
	 * seriesId passed in the parameter
	 * 
	 * @param seriesId the ID of the Series to find
	 * @return will return NoSuchElementException if no Series is found with the
	 *         provided seriesId, otherwise will return the Series
	 */
	private Series findSeriesById(Long seriesId) {
		return seriesDao.findById(seriesId)
				.orElseThrow(() -> new NoSuchElementException("series with ID=" + seriesId + " does not exists."));
	}

	/**
	 * Method will attempt to save a Series already in the database to a Author
	 * already in the database
	 * 
	 * @param authorId the ID of the author that we are trying to update
	 * @param seriesData the series info that we are adding to the Author
	 * @return will return a NoSuchElementException if the name in seriesData does not
	 * 		   match a series already in the database or a DuplicateKeyException if the 
	 * 		   Author already has the Series save to it, otherwise will return the series
	 */
	@Transactional(readOnly = false)
	public SeriesData saveSeriesToAuthor(Long authorId, SeriesData seriesData) {
		// check if series is already in dao by (series)name
		Optional<Series> opSeries = seriesDao.findByName(seriesData.getName());

		if (opSeries.isEmpty()) {
			throw new NoSuchElementException("series with name=" + seriesData.getName() + " does not exists.");
		}

		// check if series is already in the set owned by the Author
		Author author = findAuthorById(authorId);
		Set<Series> series = author.getSeries();

		for (Series authorSeries : series) {
			if (opSeries.get().getName().equals(authorSeries.getName())) {
				throw new DuplicateKeyException(
						"Series with name=" + seriesData.getName() + " already belongs to author.");
			}
		}

		// add series to author and add author to series
		opSeries.get().setAuthor(author);
		author.getSeries().add(opSeries.get());

		return new SeriesData(seriesDao.save(opSeries.get()));
	}

	/**
	 * Method will attempt to save a Series already in the database to a Illustrator
	 * already in the database
	 * 
	 * @param illustratorId the ID of the illustrator that we are trying to update
	 * @param seriesData the series info that we are adding to the Illustrator
	 * @return will return a NoSuchElementException if the name in seriesData does not
	 * 		   match a series already in the database or a DuplicateKeyException if the 
	 * 		   Illustrator already has the Series save to it, otherwise will return the series
	 */
	@Transactional(readOnly = false)
	public SeriesData saveSeriesToIllustrator(Long illustratorId, SeriesData seriesData) {
		// check if series is already in dao by (series)name
		Optional<Series> opSeries = seriesDao.findByName(seriesData.getName());

		if (opSeries.isEmpty()) {
			throw new NoSuchElementException("series with name=" + seriesData.getName() + " does not exists.");
		}

		// check if series is already in the set owned by the Author
		Illustrator illustrator = findIllustratorById(illustratorId);
		Set<Series> series = illustrator.getSeries();

		for (Series illustratorSeries : series) {
			if (opSeries.get().getName().equals(illustratorSeries.getName())) {
				throw new DuplicateKeyException(
						"Series with name=" + seriesData.getName() + " already belongs to illustrator.");
			}
		}

		// add series to author and add author to series
		opSeries.get().setIllustrator(illustrator);
		illustrator.getSeries().add(opSeries.get());

		return new SeriesData(seriesDao.save(opSeries.get()));
	}
}
