package modi.modurang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ModurangApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModurangApplication.class, args);
	}

}
