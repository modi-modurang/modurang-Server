package modi.modurang;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@OpenAPIDefinition(servers = {
        @Server(url = "/", description = "https://d2ad-221-168-22-204.ngrok-free.app/")
})

@SpringBootApplication
@ConfigurationPropertiesScan
public class ModurangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModurangApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .filter(addHeaders())
                .build();
    }

    private ExchangeFilterFunction addHeaders() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            clientRequest.headers().add("ngrok-skip-browser-warning", "true");
            return Mono.just(clientRequest);
        });
    }
}
