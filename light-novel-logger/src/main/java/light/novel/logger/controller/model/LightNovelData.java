package light.novel.logger.controller.model;

import light.novel.logger.entity.LightNovel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LightNovelData {
	private Long lightNovelId;
	private Long volumeNumber;
	private int pageCount;
	private String description;
	
	public LightNovelData(LightNovel lightNovel) {
		this.lightNovelId = lightNovel.getLightNovelId();
		this.volumeNumber = lightNovel.getVolumeNumber();
		this.pageCount = lightNovel.getPageCount();
		this.description = lightNovel.getDescription();
	}

	public LightNovel toLightNovel() {
		LightNovel lightNovel = new LightNovel();
		
		lightNovel.setLightNovelId(lightNovelId);
		lightNovel.setVolumeNumber(volumeNumber);
		lightNovel.setPageCount(pageCount);
		lightNovel.setDescription(description);
		
		return lightNovel;
	}
}
