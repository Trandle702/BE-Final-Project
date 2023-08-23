package light.novel.logger.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import light.novel.logger.controller.model.AuthorData;
import light.novel.logger.dao.AuthorDao;
import light.novel.logger.entity.Author;

@Service
public class LightNovelLoggerAuthorService {
	
	@Autowired
	private AuthorDao authorDao;

	/*
	 * CRUD operations for the Author class
	 */
	
	/**
	 * Method will either create a new Author or update currently existing Author with
	 * the authorData passed in the parameter.
	 * 
	 * @param authorData the info for the Author to be saved/updated
	 * @return will return a new/updated Author
	 */
	@Transactional(readOnly = false)
	public AuthorData saveAuthor(AuthorData authorData) {
		Author author = authorData.toAuthor();
		Author dbAuthor = authorDao.save(author);
		return new AuthorData(dbAuthor);
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
