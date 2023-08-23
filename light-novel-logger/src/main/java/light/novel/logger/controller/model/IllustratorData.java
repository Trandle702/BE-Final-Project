package light.novel.logger.controller.model;

import java.util.HashSet;
import java.util.Set;

import light.novel.logger.entity.Illustrator;
import light.novel.logger.entity.Series;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IllustratorData {
	private Long illustratorId;
	private String firstName;
	private String lastName;
	private Set<SeriesData> series = new HashSet<>();
	
	public IllustratorData(Illustrator illustrator) {
		this.illustratorId = illustrator.getIllustratorId();
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
