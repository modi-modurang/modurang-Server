package modi.modurang.domain.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "과제", description = "homework")
@RestController
@RequestMapping("/homework")
@RequiredArgsConstructor
public class HomeworkController {


}
