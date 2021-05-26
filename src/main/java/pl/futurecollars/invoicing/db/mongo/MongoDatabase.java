package pl.futurecollars.invoicing.db.mongo;

import com.mongodb.client.MongoClient;
import java.util.List;
import java.util.Optional;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class MongoDatabase implements Database {

    private final MongoClient mongoClient;

    public MongoDatabase(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public long save(Invoice invoice) {
        return 0;
    }

    @Override
    public Optional<Invoice> getById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Invoice> getAll() {
        return null;
    }

    @Override
    public Optional<Invoice> update(long id, Invoice updatedInvoice) {
        return Optional.empty();
    }

    @Override
    public Optional<Invoice> delete(long id) {
        return Optional.empty();
    }
}
