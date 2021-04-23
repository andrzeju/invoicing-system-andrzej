package pl.futurecollars.invoicing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("pl.futurecollars"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(customApiInfo())
            .tags(
                new Tag("invoice-controller", "Used to create,add,update,delete invoices")
            );
    }

    private ApiInfo customApiInfo() {
        return new ApiInfoBuilder()
            .description("This is our invoicing system documentation")
            .contact(
                new Contact(
                    "FutureCollars, 02-222 Warszawa",
                      "http://webpage.url.com",
                    "email@hosting.com"
                )
            )
            .title("Invoicing System")
            .build();
    }
}
