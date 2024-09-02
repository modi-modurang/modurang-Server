package modi.modurang.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnouncementDto {
    private Long id;
    private String title;
    private String author;
    private String content;

    public AnnouncementDto(Long id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
