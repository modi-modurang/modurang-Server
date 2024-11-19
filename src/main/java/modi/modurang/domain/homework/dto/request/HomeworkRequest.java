package modi.modurang.domain.homework.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkRequest {

    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    @NotBlank(message = "마감일을 입력해 주세요.")
    private LocalDateTime deadline;

    @NotBlank(message = "유저 아이디를 입력해 주세요.")
    private List<Long> userId;
}
