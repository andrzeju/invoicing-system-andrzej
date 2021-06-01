package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.controller.taxes.TaxCalculationResult;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Car;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

@Service
@AllArgsConstructor
public class TaxCalculationService {

    private final Database<Invoice> database;

    public TaxCalculationResult calculateTaxes(Company company) {

        BigDecimal healthInsuranceToSubstract = company.getHealthInsurance()
            .multiply(BigDecimal.valueOf(775)).divide(BigDecimal.valueOf(900), RoundingMode.HALF_UP);
        BigDecimal incomeMinusCostsMinusPensionInsuranceRounded =
            earnings(company).subtract(company.getPensionInsurance()).setScale(0, RoundingMode.HALF_DOWN);
        BigDecimal incomeTax =
            incomeMinusCostsMinusPensionInsuranceRounded.multiply(BigDecimal.valueOf(19, 2));

        return TaxCalculationResult.builder()
            .income(income(company.getTaxIdentificationNumber()))
            .costs(costs(company.getTaxIdentificationNumber()))
            .earnings(earnings(company))
            .pensionInsurance(company.getPensionInsurance())
            .healthInsurancePaid(company.getHealthInsurance())
            .healthInsuranceToSubtract(healthInsuranceToSubstract)
            .incomeMinusCostsMinusPensionInsurance(earnings(company).subtract(company.getPensionInsurance()))
            .incomeMinusCostsMinusPensionInsuranceRounded(incomeMinusCostsMinusPensionInsuranceRounded)
            .incomeTax(incomeTax)
            .incomeTaxMinusHealthInsurance(incomeTax.subtract(healthInsuranceToSubstract))
            .collectedVat(incomingVat(company.getTaxIdentificationNumber()))
            .paidVat(outgoingVat(company.getTaxIdentificationNumber()))
            .dueVat(incomingVat(company.getTaxIdentificationNumber())
                .subtract(outgoingVat(company.getTaxIdentificationNumber())))
            .finalIncomeTax(incomeTax.subtract(healthInsuranceToSubstract).setScale(0, RoundingMode.DOWN))
            .build();
    }

    private BigDecimal earnings(Company company) {
        return income(company.getTaxIdentificationNumber())
            .subtract(costs(company.getTaxIdentificationNumber()));
    }

    private BigDecimal outgoingVat(String taxIdentificationNumber) {
        return visit(this::getVatValueIncludingPersonalCarExpenses, buyerPredicate(taxIdentificationNumber));
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
        return visit(InvoiceEntry::getVatValue, sellerPredicate(taxIdentificationNumber));
    }

    private BigDecimal costs(String taxIdentificationNumber) {
        return visit(this::getPriceIncludingPersonalCarExpenses, buyerPredicate(taxIdentificationNumber));
    }

    private BigDecimal income(String taxIdentificationNumber) {
        return visit(InvoiceEntry::getPrice, sellerPredicate(taxIdentificationNumber));
    }

    private Predicate<Invoice> sellerPredicate(String taxIdentificationNumber) {
        return inv -> inv.getSeller().getTaxIdentificationNumber().equals(taxIdentificationNumber);
    }

    private Predicate<Invoice> buyerPredicate(String taxIdentificationNumber) {
        return inv -> inv.getBuyer().getTaxIdentificationNumber().equals(taxIdentificationNumber);
    }

    private BigDecimal visit(Function<InvoiceEntry, BigDecimal> amountToSum,
                             Predicate<Invoice> invoiceSourcePredicate) {
        return database.getAll().stream()
            .filter(invoiceSourcePredicate)
            .flatMap(inv -> inv.getEntries().stream())
            .map(amountToSum)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
