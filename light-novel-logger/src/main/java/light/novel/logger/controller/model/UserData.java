package light.novel.logger.controller.model;

import java.util.HashSet;
import java.util.Set;

import light.novel.logger.entity.Author;
import light.novel.logger.entity.Category;
import light.novel.logger.entity.Illustrator;
import light.novel.logger.entity.LightNovel;
import light.novel.logger.entity.Series;
import light.novel.logger.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserData {
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	//private String username;
	//private String password;
	private Set<SeriesData> series = new HashSet<>();
	
	public UserData(User user) {
		userId = user.getUserId();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		email = user.getEmail();
		
		for(Series series : user.getSeries()) {
			this.series.add(new SeriesData(series));
		}
	}
	
	public User toUser() {
		User user = new User();
		
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		
		for(SeriesData seriesData: series) {
			user.getSeries().add(seriesData.toSeries());
		}
		
		return user;
	}
	
	@Data
	@NoArgsConstructor
	public static class SeriesData{
		private Long seriesId;
		private String seriesName;
		private Set<LightNovelData> volumes = new HashSet<>();
		private Set<CategoryData> categories = new HashSet<>();
		
		public SeriesData(Series series) {
			seriesId = series.getSeriesId();
			seriesName = series.getSeriesName();
			
			for(LightNovel volume : series.getVolumes()) {
				volumes.add(new LightNovelData(volume));
			}
			
			for(Category category: series.getCategories()) {
				categories.add(new CategoryData(category));
			}
		}

		public Series toSeries() {
			Series series = new Series();
			
			series.setSeriesId(seriesId);;
			series.setSeriesName(seriesName);
			
			for(LightNovelData lightNovelData: volumes) {
				series.getVolumes().add(lightNovelData.toLightNovel());
			}
			
			for(CategoryData categoryData : categories) {
				series.getCategories().add(categoryData.toCategory());
			}
			
			return series;
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class LightNovelData{
		private Long novelId;
		private int volumeNumber;
		private int pageCount;
		private String description;
		
		public LightNovelData(LightNovel lightNovel) {
			novelId = lightNovel.getNovelId();
			volumeNumber = lightNovel.getVolumeNumber();
			pageCount = lightNovel.getPageCount();
			description = lightNovel.getDescription();
		}

		public LightNovel toLightNovel() {
			LightNovel lightNovel = new LightNovel();
			
			lightNovel.setNovelId(novelId);
			lightNovel.setVolumeNumber(volumeNumber);
			lightNovel.setPageCount(pageCount);
			lightNovel.setDescription(description);
			
			return lightNovel;
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class CategoryData{
		private Long categoryId;
		private String categoryName;
		
		public CategoryData(Category category) {
			categoryId = category.getCategoryId();
			categoryName = category.getCategoryName();
		}

		public Category toCategory() {
			Category category = new Category();
			
			category.setCategoryId(categoryId);
			category.setCategoryName(categoryName);
			
			return category;
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class AuthorData{
		private Long authorId;
		private String firstName;
		private String lastName;
		private Set<SeriesData> series = new HashSet<>();
		
		public AuthorData(Author author) {
			authorId = author.getAuthorId();
			this.firstName = author.getFirstName();
			this.lastName = author.getLastName();
			
			for (Series series : author.getSeries()) {
				this.series.add(new SeriesData(series));
			}
		}
		
		public Author toAuthor() {
			Author author = new Author();
			
			author.setAuthorId(authorId);
			author.setFirstName(firstName);
			author.setLastName(lastName);
			
			for(SeriesData seriesData: series) {
				author.getSeries().add(seriesData.toSeries());
			}
			
			return author;
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class IllustratorData{
		private Long illustratorId;
		private String firstName;
		private String lastName;
		private Set<SeriesData> series = new HashSet<>();
		
		public IllustratorData(Illustrator illustrator) {
			illustratorId = illustrator.getIllustratorId();
			this.firstName = illustrator.getFirstName();
			this.lastName = illustrator.getLastName();
			
			for(Series series : illustrator.getSeries()) {
				this.series.add(new SeriesData(series));
			}
		}
		
		public Illustrator toIllustrator() {
			Illustrator illustrator = new Illustrator();
			
			illustrator.setIllustratorId(illustratorId);
			illustrator.setFirstName(firstName);
			illustrator.setLastName(lastName);
			
			for(SeriesData seriesData : series) {
				illustrator.getSeries().add(seriesData.toSeries());
			}
			
			return illustrator;
		}
	}
}
