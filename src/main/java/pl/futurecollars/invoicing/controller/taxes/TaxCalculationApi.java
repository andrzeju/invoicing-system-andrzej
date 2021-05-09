package pl.futurecollars.invoicing.controller.taxes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.futurecollars.invoicing.model.Company;

@RequestMapping(value = "taxes", produces = {"application/json;charset=UTF-8"})
@Api(tags = {"taxes-controller"})
public interface TaxCalculationApi {

    @ApiOperation(value = "Calculate taxes and ingredients: income, costs, vat")
    @PostMapping
    TaxCalculationResult calculateTaxes(@RequestBody Company company);

}
