package light.novel.logger.controller.model;

import java.util.HashSet;
import java.util.Set;

import light.novel.logger.entity.Category;
import light.novel.logger.entity.LightNovel;
import light.novel.logger.entity.Series;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeriesData {
	private Long seriesId;
	private String seriesName;
	private Set<LightNovelData> volumes = new HashSet<>();
	private Set<CategoryData> categories = new HashSet<>();
	
	public SeriesData(Series series) {
		this.seriesId = series.getSeriesId();
		this.seriesName = series.getSeriesName();
		
		for(LightNovel volume : series.getVolumes()) {
			this.volumes.add(new LightNovelData(volume));
		}
		
		for(Category category: series.getCategories()) {
			this.categories.add(new CategoryData(category));
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
