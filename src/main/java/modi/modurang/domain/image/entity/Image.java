package modi.modurang.domain.image.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import modi.modurang.global.common.BaseEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String mimeType;
}
