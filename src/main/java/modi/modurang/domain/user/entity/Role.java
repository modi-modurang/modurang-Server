package modi.modurang.domain.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Role {

    private String club;

    private String role;
}
