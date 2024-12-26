package modi.modurang.domain.club.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modi.modurang.domain.club.enums.Club;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    @NotNull(message = "동아리는 입력해 주세요.")
    private Club club;
}