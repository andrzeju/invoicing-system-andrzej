package pl.futurecollars.invoicing.controller.taxes;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaxCalculationResult {

    private BigDecimal income;
    private BigDecimal costs;
    private BigDecimal earnings;

    private final BigDecimal pensionInsurance;
    private final BigDecimal incomeMinusCostsMinusPensionInsurance;
    private final BigDecimal incomeMinusCostsMinusPensionInsuranceRounded;
    private final BigDecimal incomeTax;
    private final BigDecimal healthInsurancePaid;
    private final BigDecimal healthInsuranceToSubtract;
    private final BigDecimal incomeTaxMinusHealthInsurance;
    private final BigDecimal finalIncomeTax;

    private BigDecimal collectedVat;
    private BigDecimal paidVat;
    private BigDecimal dueVat;
}
