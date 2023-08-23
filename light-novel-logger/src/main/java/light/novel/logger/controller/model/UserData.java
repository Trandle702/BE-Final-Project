package light.novel.logger.controller.model;

import java.util.HashSet;
import java.util.Set;

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
		this.userId = user.getUserId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		
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
}
