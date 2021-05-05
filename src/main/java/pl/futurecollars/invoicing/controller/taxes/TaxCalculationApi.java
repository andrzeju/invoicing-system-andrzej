package pl.futurecollars.invoicing.controller.taxes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("tax")
@Api(tags = {"tax-controller"})
public interface TaxCalculationApi {

    @ApiOperation(value = "Calculate taxes and ingredients: income, costs, vat")
    @GetMapping(value = "/{taxIdentificationNumber}", produces = {"application/json;charset=UTF-8"})
    TaxCalculationResult calculateTaxes(@PathVariable @ApiParam(example = "552-168-66-00") String taxIdentificationNumber);

}