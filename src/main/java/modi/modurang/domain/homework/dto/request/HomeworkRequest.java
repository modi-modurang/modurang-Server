package modi.modurang.domain.homework.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modi.modurang.domain.user.entity.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String deadline;

    @NotBlank
    private List<User> userId;
}
