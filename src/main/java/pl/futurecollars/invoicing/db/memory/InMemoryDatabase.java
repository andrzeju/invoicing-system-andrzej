package pl.futurecollars.invoicing.db.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InMemoryDatabase implements Database {

    private final HashMap<Long, Invoice> invoiceInMemoryDatabase = new HashMap<>();
    private long index = 1;

    @Override
    public long save(Invoice invoice) {
        invoice.setId(index);
        invoiceInMemoryDatabase.put(index, invoice);
        return index++;
    }

    @Override
    public Optional<Invoice> getById(long id) {
        return Optional.ofNullable(invoiceInMemoryDatabase.get(id));
    }

    @Override
    public List<Invoice> getAll() {
        return new ArrayList<>(invoiceInMemoryDatabase.values());
    }

    @Override
    public Optional<Invoice> update(long id, Invoice updatedInvoice) {
        updatedInvoice.setId(id);
        return Optional.ofNullable(invoiceInMemoryDatabase.put(id, updatedInvoice));
    }

    @Override
    public Optional<Invoice> delete(long id) {
        return Optional.ofNullable(invoiceInMemoryDatabase.remove(id));
    }
}
