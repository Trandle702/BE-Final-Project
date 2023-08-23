package light.novel.logger.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import light.novel.logger.controller.model.IllustratorData;
import light.novel.logger.dao.IllustratorDao;
import light.novel.logger.entity.Illustrator;

@Service
public class LightNovelLoggerIllustratorService {

	@Autowired
	private IllustratorDao illustratorDao;
	
	/**
	 * Method will either create a new Illustrator or update currently existing Illustrator with
	 * the illustratorData passed in the parameter.
	 * 
	 * @param illustratorData the info for the Illustrator to be saved/updated
	 * @return will return a new/updated Illustrator
	 */
	@Transactional(readOnly = false)
	public IllustratorData saveIllustrator(IllustratorData illustratorData) {
		Illustrator illustrator = illustratorData.toIllustrator();
		Illustrator dbIllustrator = illustratorDao.save(illustrator);
		return new IllustratorData(dbIllustrator);
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
