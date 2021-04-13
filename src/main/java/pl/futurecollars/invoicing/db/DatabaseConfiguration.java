package pl.futurecollars.invoicing.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Configuration
public class DatabaseConfiguration {

    @Bean
    @Primary
    public FileBasedDatabase fileBasedDatabase(IdService idService, FilesService filesService, JsonService jsonService) throws IOException {
        Path filePath = Files.createTempFile("prefix", ".txt");
        return new FileBasedDatabase(filePath, idService, filesService, jsonService);
    }

    @Bean
    public IdService idService(FilesService filesService) throws IOException {
        Path filePath = Files.createTempFile("idPrefix", ".txt");
        return new IdService(filePath, filesService);
    }

}
