package light.novel.logger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import light.novel.logger.controller.model.LightNovelData;
import light.novel.logger.service.LightNovelLoggerLightNovelService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/light_novel_logger")
@Slf4j
public class LightNovelLoggerLightNovelController {

	@Autowired
	private LightNovelLoggerLightNovelService lightNovelService;

	/*
	 * CRUD operations for the LightNovel class - Create, Read, Update
	 */
	
	/**
	 * Create CRUD operation - creates a new LightNovel
	 * 
	 * @param lightNovelData contains info to create new LightNovel
	 * @return will return a new LightNovel
	 */
	@PostMapping("user/{userId}/series/{seriesId}/light_novel")
	public LightNovelData insertLightNovel(@PathVariable Long userId, @PathVariable Long seriesId,
			@RequestBody LightNovelData lightNovelData) {
		log.info("Creating light novel={}", lightNovelData);
		return lightNovelService.saveLightNovel(userId, seriesId, lightNovelData);
	}

	/**
	 * Read CRUD operation - retrieves all LightNovels from database
	 * 
	 * @return will return a list of all the LightNovels in the database
	 */
	@GetMapping("user/{userId}/series/{seriesId}/light_novel")
	public List<LightNovelData> retrieveAllLightNovels() {
		log.info("Retrieving all light novels");
		return lightNovelService.retrieveAllLightNovels();
	}

	/**
	 * Read CRUD operation - retrieves a LightNovel by ID
	 * 
	 * @param lightNovelId the ID of the LightNovel that is getting retrieve from the database
	 * @return will return NoSuchElementException if no LightNovel is found with the
	 * 		provided lightNovelId, otherwise will return LightNovel from database
	 */
	@GetMapping("user/{userId}/series/{seriesId}/light_novel/{lightNovelId}")
	public LightNovelData retrieveLightNovelById(@PathVariable Long lightNovelId) {
		log.info("Retrieving light novel with ID={}", lightNovelId);
		return lightNovelService.retrieveLightNovelById(lightNovelId);
	}
	
	/**
	 * Update CRUD operation - updates the info of a LightNovel in the database
	 * 
	 * @param lightNovelId the ID of the LightNovel that will have their info updated
	 * @param lightNovelData contains the updated info for the LightNovel in the database
	 * @return will return NoSuchElementException if no LightNovel is found with the
	 * 		provided lightNovelId, otherwise will return the LightNovel with updated info
	 */
	@PutMapping("user/{userId}/series/{seriesId}/light_novel/{lightNovelId}")
	public LightNovelData updateLightNovel(@PathVariable Long userId, @PathVariable Long seriesId, @PathVariable Long lightNovelId, @RequestBody LightNovelData lightNovelData) {
		lightNovelData.setLightNovelId(lightNovelId);
		log.info("Upadating light nove with ID={}", lightNovelId);
		return lightNovelService.updateLightNovel(userId, seriesId, lightNovelData);
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/light_novel")
	public void deleteAllLightNovels() {
		log.info("Attempting to delete all light novels");
		throw new UnsupportedOperationException("Deleting all light novels is not allowed");
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/light_novel/{lightNovelId}")
	public void deleteLightNovelById(@PathVariable Long lightNovelId) {
		log.info("attempting to delete light novel with ID={}", lightNovelId);
		throw new UnsupportedOperationException("Deleting a light novel is not allowed.");
	}
}
