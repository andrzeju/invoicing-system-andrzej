package pl.futurecollars.invoicing.controller.invoices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.futurecollars.invoicing.model.Invoice;

@RequestMapping("invoices")
@Api(tags = "invoice-controller")
public interface InvoiceApi {

    @GetMapping(produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "Get all invoices from the database")
    List<Invoice> getAll();

    @PostMapping
    @ApiOperation(value = "Add an invoice to the database")
    int add(@RequestBody Invoice invoice);

    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "Get an invoice from the database by id")
    ResponseEntity<Invoice> getById(@PathVariable int id);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an invoice from the database by id")
    ResponseEntity<?> deleteById(@PathVariable int id);

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an invoice in the database by id")
    ResponseEntity<?> update(@PathVariable int id, @RequestBody Invoice invoice);
}
