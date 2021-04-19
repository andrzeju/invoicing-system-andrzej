package pl.futurecollars.invoicing.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    public static final String TEXT_FILE_SUFFIX = ".txt";
    public static final String PREFIX = "prefix";
    public static final String ID_PREFIX = "idPrefix";

    @Value("${invoicing.database.directory}")
    private String databaseDirectory;

    @Value("${invoicing.database.file}")
    private String invoiceFile;

    @Value("${invoicing.database.idFile}")
    private String idFile;

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "file")
    public Database fileBasedDatabase(IdService idService, FilesService filesService, JsonService jsonService) throws IOException {
        log.info("FileBased database created");
        Path filePath = Files.createTempFile(databaseDirectory, invoiceFile);
        return new FileBasedDatabase(filePath, idService, filesService, jsonService);
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "memory")
    public Database inMemoryDatabase() {
        log.info("InMemory database created");
        return new InMemoryDatabase();
    }

    @Bean
    public IdService idService(FilesService filesService) throws IOException {
        Path filePath = Files.createTempFile(databaseDirectory, idFile);
        return new IdService(filePath, filesService);
    }

}
