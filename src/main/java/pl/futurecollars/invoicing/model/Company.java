package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Company implements WithId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Company id (generated by application)", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "Tax Identification Number", required = true, example = "555-555-55-55")
    private String taxIdentificationNumber;

    @ApiModelProperty(value = "Company Address", required = true, example = "Puławska 23, 02-222 Warszawa")
    private String address;

    @ApiModelProperty(value = "Company Name", required = true, example = "Google Inc.")
    private String name;

    @ApiModelProperty(value = "Pension insurance", required = true, example = "316.74")
    @Builder.Default
    private BigDecimal pensionInsurance = BigDecimal.ZERO;

    @ApiModelProperty(value = "Health insurance", required = true, example = "115.34")
    @Builder.Default
    private BigDecimal healthInsurance = BigDecimal.ZERO;

}
