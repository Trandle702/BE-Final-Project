package light.novel.logger.controller.model;

import light.novel.logger.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryData {
	private Long categoryId;
	private String name;
	
	public CategoryData(Category category) {
		this.categoryId = category.getCategoryId();
		this.name = category.getName();
	}
	
	public CategoryData(String categoryName) {
		this.name = categoryName;
	}

	public Category toCategory() {
		Category category = new Category();
		
		category.setCategoryId(categoryId);
		category.setName(name);
		
		return category;
	}
}
