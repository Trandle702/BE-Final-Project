package light.novel.logger.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import light.novel.logger.controller.model.CategoryData;
import light.novel.logger.dao.CategoryDao;
import light.novel.logger.entity.Category;

@Service
public class LightNovelLoggerCategoryService {
	
	@Autowired
	private CategoryDao categoryDao;

	/**
	 * Method will retrieve all Categories from the database
	 * 
	 * @return will return a list of the Categories in the database
	 */
	@Transactional(readOnly = true)
	public List<CategoryData> retrieveAllCategories() {
		List<Category> categories = categoryDao.findAll();
		List<CategoryData> response = new LinkedList<>();
		
		for(Category category: categories) {
			response.add(new CategoryData(category));
		}
		
		return response;
	}

	/**
	 * Method will attempt to retrieve a Category by the categoryId passed in the
	 * parameter
	 * 
	 * @param categoryId the ID of the Category that we are trying to retrieve
	 * @return will return the Category if successful
	 */
	public CategoryData retrieveCategoryById(Long categoryId) {
		Category category = findCategoryById(categoryId);
		return new CategoryData(category);
	}

	/**
	 * Method will attempt to find a Category from the selected Series using the categoryId passed
	 * in the parameter
	 * 
	 * @param categoryId the ID of the Category to find
	 * @return will return NoSuchElementException if no Category is found with the
	 * 		provided categoryId, otherwise will return the Category
	 */
	@Transactional(readOnly = true)
	private Category findCategoryById(Long categoryId) {
		return categoryDao.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category with ID=" + categoryId + " does not exists."));
	}

}
