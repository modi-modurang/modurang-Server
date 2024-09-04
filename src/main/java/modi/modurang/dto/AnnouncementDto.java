package modi.modurang.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {

    private Long id;
    private String title;
    private String author;
    private String content;
}
