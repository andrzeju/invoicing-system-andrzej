package pl.futurecollars.invoicing.db.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "invoicing.database", havingValue = "mongo")
public class MongoDatabaseConfiguration {

    @Bean
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
    public Database<Invoice> mongoBasedDatabase(
        @Value("${invoicing.database.collection}") String collectionName,
        MongoDatabase mongoDb,
        MongoIdProvider mongoIdProvider
    ) {
        return new MongoBasedDatabase(
            mongoDb.getCollection(collectionName, Invoice.class),
            mongoIdProvider);
    }

    @Bean
    public MongoIdProvider mongoIdProvider(
        @Value("${invoicing.database.counter.collection}") String collectionName,
        MongoDatabase mongoDb
    ) {
        return new MongoIdProvider(mongoDb.getCollection(collectionName));
    }

}
