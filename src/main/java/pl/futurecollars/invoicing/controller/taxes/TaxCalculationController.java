package pl.futurecollars.invoicing.controller.taxes;

import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.service.TaxCalculationService;

@RestController
public class TaxCalculationController implements TaxCalculationApi {

    private TaxCalculationService taxCalculationService;

    @Override
    public TaxCalculationResult calculateTaxes(String taxIdentificationNumber) {
        return taxCalculationService.calculateTaxes(taxIdentificationNumber);
    }
}
