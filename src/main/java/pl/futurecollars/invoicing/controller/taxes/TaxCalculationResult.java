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

    private BigDecimal incomingVat;
    private BigDecimal outgoingVat;
    private BigDecimal dueVat;
}
