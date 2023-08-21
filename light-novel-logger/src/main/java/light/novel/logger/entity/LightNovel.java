package light.novel.logger.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class LightNovel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long novelId;
	
	private int volumeNumber;
	private int pageCount;
	private String description;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "volumes", nullable = false)
	private Series series;
}
