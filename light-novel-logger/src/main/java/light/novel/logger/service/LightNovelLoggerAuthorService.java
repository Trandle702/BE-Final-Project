package light.novel.logger.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import light.novel.logger.controller.model.AuthorData;
import light.novel.logger.dao.AuthorDao;
import light.novel.logger.dao.SeriesDao;
import light.novel.logger.dao.UserDao;
import light.novel.logger.entity.Author;
import light.novel.logger.entity.Series;
import light.novel.logger.entity.User;

@Service
public class LightNovelLoggerAuthorService {
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private SeriesDao seriesDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * Method will either create a new Author or update currently existing Author with
	 * the authorData passed in the parameter.
	 * 
	 * @param authorData the info for the Author to be saved/updated
	 * @return will return a new/updated Author
	 */
	@Transactional(readOnly = false)
	public AuthorData saveAuthor(Long userId, Long seriesId, AuthorData authorData) {
		User user = findUserById(userId);
		Series series = findSeriesById(seriesId);
		Author author = authorData.toAuthor();
		List<Author> authors = authorDao.findAll();
		
		for(Author daoAuthor : authors) {
			if(daoAuthor.getFirstName().equals(author.getFirstName()) &&
				daoAuthor.getLastName().equals(author.getLastName())) {
				daoAuthor.getSeries().add(series);
				series.setAuthor(daoAuthor);
				user.getSeries().add(series);
				return new AuthorData(authorDao.save(daoAuthor));
			}
		}
		
		author.getSeries().add(series);
		series.setAuthor(author);
		user.getSeries().add(series);
		
		return new AuthorData(authorDao.save(author));
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
	 * Method will retrieve all Authors from the database
	 * 
	 * @return will return a list of the Authors in the database
	 */
	@Transactional(readOnly = true)
	public List<AuthorData> retrieveAllAuthors() {
		List<Author> authors = authorDao.findAll();
		List<AuthorData> response = new LinkedList<>();
		
		for(Author author: authors) {
			response.add(new AuthorData(author));
		}
		
		return response;
	}

	/**
	 * Method will attempt to retrieve a Author by the authorId passed in the
	 * parameter
	 * 
	 * @param authorId the ID of the Author that we are trying to retrieve
	 * @return will return the Author if successful
	 */
	@Transactional(readOnly = true)
	public AuthorData retrieveAuthorById(Long authorId) {
		Author author = findAuthorById(authorId);
		return new AuthorData(author);
	}

	/**
	 * Method will attempt to find a Author in the database using the authorId passed in 
	 * the parameter.
	 * 
	 * @param authorId the ID of the Author to find
	 * @return will return NoSuchElementException if no Author is found with the
	 * 		provided authorId, otherwise will return the Author 
	 */
	private Author findAuthorById(Long authorId) {
		return authorDao.findById(authorId)
				.orElseThrow(() -> new NoSuchElementException(
						"Author with ID=" + authorId + " does not exists."));
	}
}
