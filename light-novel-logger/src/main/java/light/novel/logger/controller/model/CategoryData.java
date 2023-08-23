package light.novel.logger.controller.model;

import light.novel.logger.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryData {
	private Long categoryId;
	private String categoryName;
	
	public CategoryData(Category category) {
		this.categoryId = category.getCategoryId();
		this.categoryName = category.getCategoryName();
	}

	public Category toCategory() {
		Category category = new Category();
		
		category.setCategoryId(categoryId);
		category.setCategoryName(categoryName);
		
		return category;
	}
}
