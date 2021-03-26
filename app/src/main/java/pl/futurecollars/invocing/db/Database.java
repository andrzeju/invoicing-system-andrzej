package pl.futurecollars.invocing.db;

import pl.futurecollars.invocing.model.Invoice;

public interface Database {
    int create(Invoice invoice);
}
