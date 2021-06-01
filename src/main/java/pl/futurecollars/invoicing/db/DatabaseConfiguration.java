package pl.futurecollars.invoicing.db;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.db.jpa.InvoiceRepository;
import pl.futurecollars.invoicing.db.jpa.JpaDatabase;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.db.mongo.MongoBasedDatabase;
import pl.futurecollars.invoicing.db.mongo.MongoIdProvider;
import pl.futurecollars.invoicing.db.sql.SqlDatabase;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.utils.FilesService;
import pl.futurecollars.invoicing.utils.JsonService;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "file")
    public Database fileBasedDatabase(IdService idService, FilesService filesService, JsonService jsonService,
                                      @Value("${invoicing.database.directory}") String databaseDirectory,
                                      @Value("${invoicing.database.file}") String invoicesFile) throws IOException {
        Path databaseFilePath = Files.createTempFile(databaseDirectory, invoicesFile);
        log.info("FileBased database created: " + databaseFilePath.toString());
        return new FileBasedDatabase(databaseFilePath, idService, filesService, jsonService);
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "memory")
    public Database inMemoryDatabase() {
        log.info("InMemory invoice database created");
        return new InMemoryDatabase();
    }

    @Bean
    public Database<Company> inMemoryCompanyDatabase() {
        log.info("InMemory company database created");
        return new InMemoryDatabase();
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "jpa")
    public Database jpaDatabase(InvoiceRepository invoiceRepository) {
        return new JpaDatabase(invoiceRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "sql")
    public Database sqlDatabase(JdbcTemplate jdbcTemplate) {
        log.info("SQL database created");
        return new SqlDatabase(jdbcTemplate);
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "file")
    public IdService idService(
        FilesService filesService,
        @Value("${invoicing.database.directory}") String databaseDirectory,
        @Value("${invoicing.database.idFile}") String idFile
    ) throws IOException {
        Path idFilePath = Files.createTempFile(databaseDirectory, idFile);
        return new IdService(idFilePath, filesService);
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "mongo")
    public MongoDatabase mongoDb(
        @Value("${invoicing.database.name}") String databaseName
    ) {
        CodecRegistry codecRegistry = CodecRegistries
            .fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings
                .builder()
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase(databaseName);
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "mongo")
    public Database mongoBasedDatabase(
        @Value("${invoicing.database.collection}") String collectionName,
        MongoDatabase mongoDb,
        MongoIdProvider mongoIdProvider
    ) {
        return new MongoBasedDatabase(
            mongoDb.getCollection(collectionName, Invoice.class),
            mongoIdProvider);
    }

    @Bean
    @ConditionalOnProperty(name = "invoicing.database", havingValue = "mongo")
    public MongoIdProvider mongoIdProvider(
        @Value("${invoicing.database.counter.collection}") String collectionName,
        MongoDatabase mongoDb
    ) {
        return new MongoIdProvider(mongoDb.getCollection(collectionName));
    }

}
