package pl.futurecollars.invoicing.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceEntry {

    private String description;
    private BigDecimal price;
    private int quantity;
    private BigDecimal vatValue;
    private Vat vatRate;

}
