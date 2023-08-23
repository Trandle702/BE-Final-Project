package light.novel.logger.controller;

import java.util.List;

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

import light.novel.logger.controller.model.IllustratorData;
import light.novel.logger.service.LightNovelLoggerIllustratorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("light_novel_logger")
@Slf4j
public class LightNovelLoggerIllustratorController {

	@Autowired
	private LightNovelLoggerIllustratorService illustratorService;

	/**
	 * Create CRUD operation - creates a new Illustrator
	 * 
	 * @param illustratorData contains info to create new Illustrator
	 * @return will return a new Illustrator
	 */
	@PostMapping("/illustrator")
	@ResponseStatus(code = HttpStatus.CREATED)
	public IllustratorData insertIllustrator(@RequestBody IllustratorData illustratorData) {
		log.info("Creating new illustrator={}", illustratorData);
		return illustratorService.saveIllustrator(illustratorData);
	}

	/**
	 * Read CRUD operation - retrieves all Illustrators from database
	 * 
	 * @return will return a list of all the Illustrators in the database
	 */
	@GetMapping("/illustrator")
	public List<IllustratorData> retrieveAllIllustrators() {
		log.info("Retrieving all illustrators");
		return illustratorService.retrieveAllIllustrators();
	}

	/**
	 * Read CRUD operation - retrieves a Illustrator by ID
	 * 
	 * @param illustratorId the ID of the Illustrator that is getting retrieve from the database
	 * @return will return NoSuchElementException if no Illustrator is found with the
	 * 		provided illustratorId, otherwise will return Illustrator from database
	 */
	@GetMapping("/illustrator/{illustratorId}")
	public IllustratorData retrieveIllustratorById(@PathVariable Long illustratorId) {
		log.info("retrieving illustrator with ID={}", illustratorId);
		return illustratorService.retrieveIllustratorById(illustratorId);
	}

	/**
	 * Update CRUD operation - updates the info of a Illustrator in the database
	 * 
	 * @param illustratorId the ID of the Illustrator that will have their info updated
	 * @param illustratorData contains the updated info for the Illustrator in the database
	 * @return will return NoSuchElementException if no Illustrator is found with the
	 * 		provided illustratorId, otherwise will return the Illustrator with updated info
	 */
	@PutMapping("/illustrator/{illustratorId}")
	public IllustratorData updateIllustrator(@PathVariable Long illustratorId,
			@RequestBody IllustratorData illustratorData) {
		illustratorData.setIllustratorId(illustratorId);
		log.info("Updating illustrator with ID={}", illustratorId);
		return illustratorService.saveIllustrator(illustratorData);
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/illustrator")
	public void deleteAllIllustrators() {
		log.info("Attempting to delete all illustrators");
		throw new UnsupportedOperationException("Deleting all illustrators is not allowed");
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/illustrator/{illustratorId}")
	public void delteIllustratorById(@PathVariable Long illustratorId) {
		log.info("attempting to delete illustrator with ID={}", illustratorId);
		throw new UnsupportedOperationException("Deleting an illustrator is not allowed.");
	}
}
