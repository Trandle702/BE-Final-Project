package light.novel.logger.controller.model;

import java.util.HashSet;
import java.util.Set;

import light.novel.logger.entity.Author;
import light.novel.logger.entity.Series;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorData {
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
