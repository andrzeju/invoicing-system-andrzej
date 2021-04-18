package pl.futurecollars.invoicing.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Configuration
public class DatabaseConfiguration {

    public static final String TEXT_FILE_SUFFIX = ".txt";
    public static final String PREFIX = "prefix";
    public static final String ID_PREFIX = "idPrefix";

    @Bean
    @Profile("prod")
    public Database fileBasedDatabase(IdService idService, FilesService filesService, JsonService jsonService) throws IOException {
        Path filePath = Files.createTempFile(PREFIX, TEXT_FILE_SUFFIX);
        return new FileBasedDatabase(filePath, idService, filesService, jsonService);
    }

    @Bean
    @Profile("dev")
    public Database inMemoryDatabase() {
        return new InMemoryDatabase();
    }

    @Bean
    public IdService idService(FilesService filesService) throws IOException {
        Path filePath = Files.createTempFile(ID_PREFIX, TEXT_FILE_SUFFIX);
        return new IdService(filePath, filesService);
    }

}
