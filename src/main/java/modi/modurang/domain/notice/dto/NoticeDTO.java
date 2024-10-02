package modi.modurang.domain.notice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class NoticeDTO {

    private Long id;

    @Column(nullable = false)
    @Size(min = 1, max = 25)
    private String title;

    @Column(nullable = false)
    @Size(min = 1, max = 1000)
    private String content;

    @Column(nullable = false)
    private String author;

    public NoticeDTO(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
