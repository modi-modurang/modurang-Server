package modi.modurang.domain.image.repository;


import modi.modurang.domain.image.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Integer> {
}
