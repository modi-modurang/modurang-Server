package modi.modurang.global.config.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

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
