package modi.modurang.domain.homework.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkRequest {

    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    @NotBlank(message = "마감일은 필수 항목입니다.")
    private String deadline;

    @NotBlank(message = "유저 아이디는 필수 항목입니다.")
    private List<Long> userId;
}
