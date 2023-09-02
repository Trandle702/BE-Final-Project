package light.novel.logger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import light.novel.logger.controller.model.SeriesData;
import light.novel.logger.service.LightNovelLoggerSeriesService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/light_novel_logger")
@Slf4j
public class LightNovelLoggerSeriesController {

	@Autowired
	private LightNovelLoggerSeriesService seriesService;

	/*
	 * CRUD operations for the Series class - Create, Read, Update
	 */
	
	/**
	 * Create CRUD operation - creates a new Series
	 * 
	 * @param userId is Id of the selected user  
	 * @param seriesData contains info to create new Series
	 * @return will return a new Series
	 */
	@PostMapping("/user/{userId}/series")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SeriesData insertSeries(@PathVariable Long userId, @RequestBody SeriesData seriesData) {
		log.info("Creating new series={}", seriesData);
		return seriesService.saveSeries(userId, seriesData);
	}

	/**
	 * Read CRUD operation - retrieves all Series from the selected User
	 * 
	 * @return will return a list of all the Series from the selected User
	 */
	@GetMapping("/user/{userId}/series")
	public List<SeriesData> retrieveAllSeries(@PathVariable Long userId) {
		log.info("Retrieving all series.");
		return seriesService.retrieveAllSeries(userId);
	}

	/**
	 * Read CRUD operation - retrieves a Series by ID
	 * 
	 * @param userId the ID of the selected User
	 * @param seriesId the ID of the Series that is getting retrieve the selected User
	 * @return will return NoSuchElementException if no Series or User is found with the
	 * 		provided seriesId and userId, otherwise will return a Series
	 */
	@GetMapping("/user/{userId}/series/{seriesId}")
	public SeriesData retrieveSeriesById(@PathVariable Long seriesId) {
		log.info("Retrieving series with ID={}", seriesId);
		return seriesService.retrieveSeriesById(seriesId);
	}

	/**
	 * Update CRUD operation - updates the info of a Series from the selected User
	 * 
	 * @param userId the ID of the selected User
	 * @param seriesId the ID of the Series that will be updated
	 * @param seriesData contains the updated info for the Series
	 * @return will return NoSuchElementException if no Series or User is found with the
	 * 		provided seriesId and userId, otherwise will return the updated Series
	 */
	@PutMapping("/user/{userId}/series/{seriesId}")
	public SeriesData updateSeries(@PathVariable Long userId, @PathVariable Long seriesId,
			@RequestBody SeriesData seriesData) {
		seriesData.setSeriesId(seriesId);
		log.info("Updating series with ID={}", seriesId);
		return seriesService.updateSeries(userId, seriesData);
	}
	
	/**
	 * Update CRUD operation - adds a Series in the database to an Author 
	 * 
	 * @param authorId the ID of the author were are updating
	 * @param seriesData contains the series info that will be added to the Author
	 * @return will return a NoSuchElementException if the name in seriesData does not
	 * 		   match a series already in the database or a DuplicateKeyException if the 
	 * 		   Author already has the Series save to it, otherwise will return the series
	 */
	@PutMapping("author/{authorId}/series")
	public SeriesData insertSeriesToAuthor(@PathVariable Long authorId, @RequestBody SeriesData seriesData) {
		log.info("");
		return seriesService.saveSeriesToAuthor(authorId, seriesData);
	}
	
	/**
	 * Update CRUD operation - adds a Series in the database to an Illustrator
	 * 
	 * @param illustratorId the ID of the illustrator were are updating
	 * @param seriesData contains the series info that will be added to the Illustrator
	 * @return will return a NoSuchElementException if the name in seriesData does not
	 * 		   match a series already in the database or a DuplicateKeyException if the 
	 * 		   Illustrator already has the Series save to it, otherwise will return the series
	 */
	@PutMapping("illustrator/{illustratorId}/series")
	public SeriesData insertSeriesToIllustrator(@PathVariable Long illustratorId, @RequestBody SeriesData seriesData) {
		log.info("");
		return seriesService.saveSeriesToIllustrator(illustratorId, seriesData);
	}
}
