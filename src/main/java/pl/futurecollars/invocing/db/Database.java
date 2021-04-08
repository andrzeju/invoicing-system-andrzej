package pl.futurecollars.invocing.db;

import java.util.List;
import java.util.Optional;
import pl.futurecollars.invocing.model.Invoice;

public interface Database {

    int save(Invoice invoice);

    Optional<Invoice> getById(int id);

    List<Invoice> getAll();

    Optional<Invoice> update(int id, Invoice updatedInvoice);

    Optional<Invoice> delete(int id);

}
