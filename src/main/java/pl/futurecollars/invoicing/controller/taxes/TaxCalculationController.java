package pl.futurecollars.invoicing.controller.taxes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taxes")
public class TaxCalculationController {

    @GetMapping(produces = {"application/json;charset=UTF-8"})
    public TaxCalculationResult calculateTaxes(String taxIdentificationNumber) {
        return null;
    }
}
