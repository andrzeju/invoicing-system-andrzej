package pl.futurecollars.invoicing.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    private int id;
    private LocalDate date;
    private Company buyer;
    private Company seller;
    private List<InvoiceEntry> entries;
}
