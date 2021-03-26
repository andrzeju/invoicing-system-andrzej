package pl.futurecollars.invocing.db;

import java.util.HashMap;
import pl.futurecollars.invocing.model.Invoice;

public class InMemoryDatabase implements Database {

    private HashMap<Integer, Invoice> invoiceInMememoryDatabase = new HashMap<>();
    private int index = 1;

    @Override
    public int create(Invoice invoice) {
        invoiceInMememoryDatabase.put(index, invoice);
        return index++;
    }
}
