package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Company {

    @ApiModelProperty(value = "Tax Identification Number", required = true, example = "555-555-55-55")
    private String taxIdentificationNumber;

    @ApiModelProperty(value = "Company Address", required = true, example = "Puławska 23, 02-222 Warszawa")
    private String address;

    @ApiModelProperty(value = "Company Name", required = true, example = "Google Inc.")
    private String name;

    public Company(String taxIdentificationNumber, String address, String name) {
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.address = address;
        this.name = name;
    }
}
