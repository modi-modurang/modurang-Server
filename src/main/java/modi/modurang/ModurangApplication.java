package modi.modurang;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public CommandLineRunner commandLineRunner(Dotenv dotenv) {
        return args -> {
            String dbUrl = dotenv.get("DATABASE_URL");
            String dbUsername = dotenv.get("DATABASE_USERNAME");
            String dbPassword = dotenv.get("DATABASE_PASSWORD");
            String emailUsername = dotenv.get("EMAIL_USERNAME");
            String emailPassword = dotenv.get("EMAIL_PASSWORD");
            String jwtAccessTokenSecretKey = dotenv.get("JWT_ACCESS_TOKEN_SECRET_KEY");
            String jwtAccessTokenExpiration = dotenv.get("JWT_ACCESS_TOKEN_EXPIRATION");
            String jwtRefreshTokenSecretKey = dotenv.get("JWT_REFRESH_TOKEN_SECRET_KEY");
            String jwtRefreshTokenExpiration = dotenv.get("JWT_REFRESH_TOKEN_EXPIRATION");

            System.out.println("Database URL: " + dbUrl);
            System.out.println("Database Username: " + dbUsername);
            System.out.println("Database Password: " + dbPassword);
            System.out.println("Email Username: " + emailUsername);
            System.out.println("Email Password: " + emailPassword);
            System.out.println("JWT Access Token Secret Key: " + jwtAccessTokenSecretKey);
            System.out.println("JWT Access Token Expiration: " + jwtAccessTokenExpiration);
            System.out.println("JWT Refresh Token Secret Key: " + jwtRefreshTokenSecretKey);
            System.out.println("JWT Refresh Token Expiration: " + jwtRefreshTokenExpiration);
        };
    }
}
