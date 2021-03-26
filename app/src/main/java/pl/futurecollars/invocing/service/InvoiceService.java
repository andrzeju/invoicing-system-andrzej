package pl.futurecollars.invocing.service;

import pl.futurecollars.invocing.db.Database;
import pl.futurecollars.invocing.model.Invoice;

public class InvoiceService {

    private final Database db;

    public InvoiceService(Database db) {
        this.db = db;
    }

    public int create(Invoice invoice) {
        int id = db.create(invoice);
        return id;
    }

//    update

//    getById

//    delete

//    getAll
}

