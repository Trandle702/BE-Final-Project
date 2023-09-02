package light.novel.logger.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import light.novel.logger.controller.model.AuthorData;
import light.novel.logger.service.LightNovelLoggerAuthorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("light_novel_logger")
@Slf4j
public class LightNovelLoggerAuthorController {

	@Autowired
	private LightNovelLoggerAuthorService authorService;
	
	/*
	 * CRUD operations for the Author class - Create, Read, Update
	 */
	
	/**
	 * Create CRUD operation - creates a new Author
	 * 
	 * @param authorData contains info to create new Author
	 * @return will return a new Author
	 */
	@PostMapping("/user/{userId}/series/{seriesId}/author")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AuthorData insertAuthor(@PathVariable Long userId, @PathVariable Long seriesId, @RequestBody AuthorData authorData) {
		log.info("Creating new author {}", authorData);
		return authorService.saveAuthor(userId, seriesId, authorData);
	}
	
	/**
	 * Read CRUD operation - retrieves all Authors from database
	 * 
	 * @return will return a list of all the Authors in the database
	 */
	@GetMapping("/author")
	public List<AuthorData> retrieveAllAuthors(){
		log.info("Retrieving all authors.");
		return authorService.retrieveAllAuthors();
	}
	
	/**
	 * Read CRUD operation - retrieves a Author by ID
	 * 
	 * @param authorId the ID of the Author that is getting retrieve from the database
	 * @return will return NoSuchElementException if no Author is found with the
	 * 		provided authorId, otherwise will return Author from database
	 */
	@GetMapping("/author/{authorId}")
	public AuthorData retrieveAuthorById(@PathVariable Long authorId) {
		log.info("Retrieving author with ID={}", authorId);
		return authorService.retrieveAuthorById(authorId);
	}
	
	/**
	 * Update CRUD operation - updates the info of a Author in the database
	 * 
	 * @param authorId the ID of the Author that will have their info updated
	 * @param authorData contains the updated info for the Author in the database
	 * @return will return NoSuchElementException if no Author is found with the
	 * 		provided authorId, otherwise will return the Author with updated info
	 */
	@PutMapping("user/{userId}/series/{seriesId}/author/{authorId}")
	public AuthorData updateAuthor(@PathVariable Long userId, @PathVariable Long seriesId, @PathVariable Long authorId, @RequestBody AuthorData authorData) {
		authorData.setAuthorId(authorId);
		log.info("Updating author with ID={}", authorId);
		return authorService.saveAuthor(userId, seriesId, authorData);
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/author")
	public void deleteAllAuthors() {
		log.info("Attempting to delete all authors");
		throw new UnsupportedOperationException("Deleting all authors is not allowed");
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/author/{authorId}")
	public Map<String, String> deleteAuthorById(@PathVariable Long authorId) {
		log.info("Attempting to delete author with ID={}", authorId);
		throw new UnsupportedOperationException("Deleting an author by ID is not allowed");
	}
}
