package light.novel.logger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import light.novel.logger.controller.model.CategoryData;
import light.novel.logger.service.LightNovelLoggerCategoryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/light_novel_logger")
@Slf4j
public class LightNovelLoggerCategoryController {
	
	@Autowired
	private LightNovelLoggerCategoryService categoryService;
	
	/*
	 * CRUD operations for the Category class - Read
	 */
	
	/**
	 * Create CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@PostMapping("/category")
	public void insertCategory() {
		log.info("Attempting to create a category");
		throw new UnsupportedOperationException("Creating a category is not allowed");
	}
	
	/**
	 * Read CRUD operation - retrieves all Categories from database
	 * 
	 * @return
	 */
	@GetMapping("/category")
	public List<CategoryData> retrieveAllCategories(){
		log.info("Retrieving all categories.");
		return categoryService.retrieveAllCategories();
	}
	
	/**
	 * Read CRUD operation - retrieves a Category by ID
	 * 
	 * @param categoryId the ID of the Category that is getting retrieved
	 * @return will return NoSuchElementException if no Category is found with the
	 * 		provided categoryId, otherwise will return Category from database
	 */
	@GetMapping("/category/{categoryId}")
	public CategoryData retrieveCategoryById(@PathVariable Long categoryId) {
		log.info("Retrieving category with ID={}", categoryId);
		return categoryService.retrieveCategoryById(categoryId);
	}
	
	/**
	 * Update CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@PutMapping("/category")
	public void updateCategory() {
		log.info("Attempting to update a category");
		throw new UnsupportedOperationException("Updating a category is not allowed");
	}
	
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/category")
	public void deleteAllCategories() {
		log.info("Attempting to delete all category");
		throw new UnsupportedOperationException("Deleting all categories is not allowed");
	}
		
	/**
	 * Delete CRUD operation - will throw an UnsupportedOperationException
	 * 
	 */
	@DeleteMapping("/category/{categoryId}")
	public void deleteCategoryById(@PathVariable Long categoryId) {
		log.info("Attempting to delete category with ID={}", categoryId);
		throw new UnsupportedOperationException("Deleting a category by ID is not allowed");
	}
}
