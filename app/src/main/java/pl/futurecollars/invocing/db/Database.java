package pl.futurecollars.invocing.db;

import java.util.List;
import java.util.Optional;
import pl.futurecollars.invocing.model.Invoice;

public interface Database {

    int save(Invoice invoice);

    Optional<Invoice> getById(int id);

    List<Invoice> getAll();

    void update(int id, Invoice updatedInvoice);

    void delete(int id);

}
