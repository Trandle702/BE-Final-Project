package light.novel.logger.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Series {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seriesId;
	
	private String seriesName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "series",
				cascade = CascadeType.ALL,
				orphanRemoval = true)
	private Set<LightNovel> volumes = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id", nullable = false)
	private Author author;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "illustrator_id", nullable = false)
	private Illustrator illustrator;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade= CascadeType.PERSIST)
	@JoinTable(
			name = "series_category",
			joinColumns = @JoinColumn(name = "series_id"),
			inverseJoinColumns =  @JoinColumn(name = "category_id")
	)
	private Set<Category> categories = new HashSet<>();
}
