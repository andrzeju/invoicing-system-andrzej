package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.controller.taxes.TaxCalculationResult;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

@Service
@AllArgsConstructor
public class TaxCalculationService {

    private Database database;

    public TaxCalculationResult calculateTaxes(String taxIdentificationNumber) {
        return TaxCalculationResult.builder()
            .income(income(taxIdentificationNumber))
            .costs(costs(taxIdentificationNumber))
            .earnings(income(taxIdentificationNumber).subtract(costs(taxIdentificationNumber)))
            .incomingVat(incomingVat(taxIdentificationNumber))
            .outgoingVat(outgoingVat(taxIdentificationNumber))
            .dueVat(incomingVat(taxIdentificationNumber).subtract(outgoingVat(taxIdentificationNumber)))
            .build();
    }

    private BigDecimal outgoingVat(String taxIdentificationNumber) {
        return database.visit(InvoiceEntry::getVatValue, buyerPredicate(taxIdentificationNumber));
    }

    private BigDecimal incomingVat(String taxIdentificationNumber) {
        return database.visit(InvoiceEntry::getVatValue, sellerPredicate(taxIdentificationNumber));
    }

    private BigDecimal costs(String taxIdentificationNumber) {
        return database.visit(InvoiceEntry::getPrice, buyerPredicate(taxIdentificationNumber));
    }

    private BigDecimal income(String taxIdentificationNumber) {
        return database.visit(InvoiceEntry::getPrice, sellerPredicate(taxIdentificationNumber));
    }

    private Predicate<Invoice> sellerPredicate(String taxIdentificationNumber) {
        return inv -> inv.getSeller().getTaxIdentificationNumber().equals(taxIdentificationNumber);
    }

    private Predicate<Invoice> buyerPredicate(String taxIdentificationNumber) {
        return inv -> inv.getBuyer().getTaxIdentificationNumber().equals(taxIdentificationNumber);
    }
}
