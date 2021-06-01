package pl.futurecollars.invoicing.db.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;

@Configuration
@Slf4j
public class InMemoryDatabaseConfiguration {

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "memory")
    public Database<Invoice> inMemoryDatabase() {
        log.info("InMemory invoice database created");
        return new InMemoryDatabase();
    }

    @Bean
    public Database<Company> inMemoryCompanyDatabase() {
        log.info("InMemory company database created");
        return new InMemoryDatabase();
    }

}
