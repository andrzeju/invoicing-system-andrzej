package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.futurecollars.invoicing.db.WithId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Invoice implements WithId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Invoice id (generated by application)", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "Invoice number (assigned by user)", required = true, example = "2020/03/08/0000001")
    private String number;

    @ApiModelProperty(value = "Date invoice was created", required = true, example = "2021-05-12")
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer")
    @ApiModelProperty(required = true)
    private Company buyer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller")
    @ApiModelProperty(required = true)
    private Company seller;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "invoice_invoice_entry", inverseJoinColumns = @JoinColumn(name = "invoice_entry_id"))
    @ApiModelProperty(required = true)
    private List<InvoiceEntry> entries;
}
