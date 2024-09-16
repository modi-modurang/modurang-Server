package modi.modurang;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(servers = {
        @Server(description = "https://0d82-221-168-22-204.ngrok-free.app")
})

@SpringBootApplication
@ConfigurationPropertiesScan
public class ModurangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModurangApplication.class, args);
    }

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
}
