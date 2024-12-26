package modi.modurang.domain.notice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoticeResponse {
    private String title;

    private String content;

    private String writer;
}