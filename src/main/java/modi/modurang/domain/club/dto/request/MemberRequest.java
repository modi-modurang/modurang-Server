package modi.modurang.domain.club.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modi.modurang.domain.club.enums.Club;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

    private Club club;
}
