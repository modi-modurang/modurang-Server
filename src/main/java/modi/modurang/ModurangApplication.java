package modi.modurang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication // Spring Boot 애플리케이션을 정의하는 어노테이션으로, @Configuration, @EnableAutoConfiguration, @ComponentScan을 포함합니다.
@ConfigurationPropertiesScan // @ConfigurationProperties 어노테이션이 붙은 클래스를 자동으로 스캔하여 프로퍼티를 바인딩합니다.
public class ModurangApplication {

	// Spring Boot 애플리케이션의 진입점입니다.
	public static void main(String[] args) {
		// SpringApplication.run() 메서드를 호출하여 Spring Boot 애플리케이션을 시작합니다.
		SpringApplication.run(ModurangApplication.class, args);
	}

}
