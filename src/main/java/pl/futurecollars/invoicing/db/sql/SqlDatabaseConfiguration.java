package pl.futurecollars.invoicing.db.sql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "invoicing.database", havingValue = "sql")
public class SqlDatabaseConfiguration {

    @Bean
    public Database<Invoice> sqlDatabase(JdbcTemplate jdbcTemplate) {
        log.info("SQL database created");
        return new SqlDatabase(jdbcTemplate);
    }

}
