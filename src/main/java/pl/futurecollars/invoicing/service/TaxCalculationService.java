package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.controller.taxes.TaxCalculationResult;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Car;
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
        return database.visit(this::getVatValueIncludingPersonalCarExpenses, buyerPredicate(taxIdentificationNumber));
    }

    private BigDecimal getVatValueIncludingPersonalCarExpenses(InvoiceEntry invoiceEntry) {
        return Optional.ofNullable(invoiceEntry.getCarRelatedExpense())
            .map(Car::isIncludingPersonalUse)
            .map(personalUse -> personalUse ? BigDecimal.valueOf(0.5) : BigDecimal.ONE)
            .map(multiplier -> invoiceEntry.getVatValue().multiply(multiplier))
            .map(value -> value.setScale(2, RoundingMode.DOWN))
            .orElse(invoiceEntry.getVatValue());
    }

    private BigDecimal getPriceIncludingPersonalCarExpenses(InvoiceEntry invoiceEntry) {
        return invoiceEntry.getPrice()
            .add(invoiceEntry.getVatValue())
            .subtract(getVatValueIncludingPersonalCarExpenses(invoiceEntry));
    }

    private BigDecimal incomingVat(String taxIdentificationNumber) {
        return database.visit(InvoiceEntry::getVatValue, sellerPredicate(taxIdentificationNumber));
    }

    private BigDecimal costs(String taxIdentificationNumber) {
        return database.visit(this::getPriceIncludingPersonalCarExpenses, buyerPredicate(taxIdentificationNumber));
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
