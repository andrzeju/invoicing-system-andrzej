package pl.futurecollars.invoicing.db.mongo;

import com.mongodb.client.MongoCollection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.util.Streamable;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@RequiredArgsConstructor
public class MongoBasedDatabase implements Database<Invoice> {

    private final MongoCollection<Invoice> mongoDatabase;
    private final MongoIdProvider mongoIdProvider;

    @Override
    public long save(Invoice invoice) {
        long id = mongoIdProvider.getNextIdAndIncrement();
        invoice.setId(id);
        mongoDatabase.insertOne(invoice);
        return id;
    }

    @Override
    public Optional<Invoice> getById(long id) {
        return Optional.ofNullable(mongoDatabase.find(idFilter(id)).first());
    }

    private Document idFilter(long id) {
        return new Document("_id", id);
    }

    @Override
    public List<Invoice> getAll() {
        return Streamable.of(mongoDatabase.find()).toList();
    }

    @Override
    public Optional<Invoice> update(long id, Invoice updatedInvoice) {
        updatedInvoice.setId(id);
        return Optional.ofNullable(mongoDatabase.findOneAndReplace(idFilter(id), updatedInvoice));
    }

    @Override
    public Optional<Invoice> delete(long id) {
        return Optional.ofNullable(mongoDatabase.findOneAndDelete(idFilter(id)));
    }
}
