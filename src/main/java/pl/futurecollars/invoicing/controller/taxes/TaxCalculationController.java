package pl.futurecollars.invoicing.controller.taxes;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.service.TaxCalculationService;

@RestController
@AllArgsConstructor
public class TaxCalculationController implements TaxCalculationApi {

    private TaxCalculationService taxCalculationService;

    @Override
    public TaxCalculationResult calculateTaxes(String taxIdentificationNumber) {
        return taxCalculationService.calculateTaxes(taxIdentificationNumber);
    }
}
