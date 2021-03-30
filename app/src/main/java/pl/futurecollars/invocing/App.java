package pl.futurecollars.invocing;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import pl.futurecollars.invocing.config.Config;
import pl.futurecollars.invocing.db.Database;
import pl.futurecollars.invocing.db.file.FileBasedDatabase;
import pl.futurecollars.invocing.db.file.IdService;
import pl.futurecollars.invocing.model.Company;
import pl.futurecollars.invocing.model.Invoice;
import pl.futurecollars.invocing.model.InvoiceEntry;
import pl.futurecollars.invocing.model.Vat;
import pl.futurecollars.invocing.service.InvoiceService;
import pl.futurecollars.invocing.utils.FilesService;
import pl.futurecollars.invocing.utils.JsonService;

public class App {

    public static void main(String[] args) {

        FilesService filesService = new FilesService();
        IdService idService = new IdService(Path.of(Config.ID_FILE_LOCATION), filesService);
        JsonService jsonService = new JsonService();
        Database db = new FileBasedDatabase(Path.of(Config.DATABASE_LOCATION), idService, filesService, jsonService);

        InvoiceService service = new InvoiceService(db);

        Company buyer = new Company("5213861303", "ul. Bukowińska 24d/7 02-703 Warszawa, Polska", "iCode Trust Sp. z o.o");
        Company seller = new Company("552-168-66-00", "32-005 Niepolomice, Nagietkowa 19", "Piotr Kolacz Development");

        List<InvoiceEntry> products =
            List.of(new InvoiceEntry("Programming course", BigDecimal.valueOf(10000), BigDecimal.valueOf(2300), Vat.VAT_23));

        Invoice invoice = new Invoice(LocalDate.now(), buyer, seller, products);

        int id = service.save(invoice);

        service.getById(id).ifPresent(System.out::println);

        System.out.println(service.getAll());

        service.delete(id);

    }
}

