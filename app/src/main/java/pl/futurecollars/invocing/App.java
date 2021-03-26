package pl.futurecollars.invocing;

import pl.futurecollars.invocing.db.Database;
import pl.futurecollars.invocing.db.InMemoryDatabase;
import pl.futurecollars.invocing.model.Invoice;
import pl.futurecollars.invocing.service.InvoiceService;

public class App {


    public static void main(String[] args) {

        Database db = new InMemoryDatabase();

        InvoiceService invoiceService = new InvoiceService(db);

        Invoice invoice = new Invoice();
        invoiceService.create(invoice);

    }
}

