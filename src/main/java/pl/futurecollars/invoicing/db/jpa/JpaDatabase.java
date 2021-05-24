package pl.futurecollars.invoicing.db.jpa;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Streamable;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@AllArgsConstructor
public class JpaDatabase implements Database {

    private final InvoiceRepository invoiceRepository;

    @Override
    public int save(Invoice invoice) {
        return invoiceRepository.save(invoice).getId();
    }

    @Override
    public Optional<Invoice> getById(int id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> getAll() {
        return Streamable.of(invoiceRepository.findAll()).toList();
    }

    @Override
    public Optional<Invoice> update(int id, Invoice updatedInvoice) {
        Optional<Invoice> invoice = getById(id);
        if (invoice.isPresent()) {
            Invoice savedInvoice = invoice.get();

            updatedInvoice.setId(id);
            updatedInvoice.getBuyer().setId(savedInvoice.getBuyer().getId());
            updatedInvoice.getSeller().setId(savedInvoice.getSeller().getId());

            invoiceRepository.save(updatedInvoice);
        }

        return invoice;
    }

    @Override
    public Optional<Invoice> delete(int id) {
        Optional<Invoice> invoice = getById(id);
        invoice.ifPresent(invoiceRepository :: delete);

        return invoice;
    }
}