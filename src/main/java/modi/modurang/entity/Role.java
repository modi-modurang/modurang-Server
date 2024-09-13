package modi.modurang.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Role {
    private String studentClub;

    private String role;
}
