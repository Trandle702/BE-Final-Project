package light.novel.logger.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Illustrator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long illustratorId;
	private String firstName;
	private String lastName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "illustrator",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Series> series;
}
