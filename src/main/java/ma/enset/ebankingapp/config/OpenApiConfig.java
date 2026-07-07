package ma.enset.ebankingapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Banking Application API")
                        .version("1.0")
                        .description("REST API documentation for the E-Banking application.")
                        .contact(new Contact()
                                .name("E-Banking Team")
                                .email("support@ebanking.ma")));
    }
}
