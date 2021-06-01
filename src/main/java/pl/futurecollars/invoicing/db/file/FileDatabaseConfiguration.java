package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "invoicing.database", havingValue = "file")
public class FileDatabaseConfiguration {

    @Bean
    public IdService idService(
        FilesService filesService,
        @Value("${invoicing.database.directory}") String databaseDirectory,
        @Value("${invoicing.database.idFile}") String idFile
    ) throws IOException {
        Path idFilePath = Files.createTempFile(databaseDirectory, idFile);
        return new IdService(idFilePath, filesService);
    }

    @Bean
    public Database fileBasedDatabase(IdService idService, FilesService filesService, JsonService jsonService,
                                      @Value("${invoicing.database.directory}") String databaseDirectory,
                                      @Value("${invoicing.database.file}") String invoicesFile) throws IOException {
        Path databaseFilePath = Files.createTempFile(databaseDirectory, invoicesFile);
        log.info("FileBased database created: " + databaseFilePath.toString());
        return new FileBasedDatabase(databaseFilePath, idService, filesService, jsonService);
    }
}
