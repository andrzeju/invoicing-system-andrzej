package pl.futurecollars.invoicing.controller.companies;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.futurecollars.invoicing.model.Company;

@CrossOrigin
@RequestMapping("companies")
@Api(tags = "company-controller")
public interface CompanyApi {

    @GetMapping(produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "Get all companies from the database")
    List<Company> getAll();

    @PostMapping
    @ApiOperation(value = "Add a company to the database")
    long add(@RequestBody Company invoice);

    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "Get a company from the database by id")
    ResponseEntity<Company> getById(@PathVariable int id);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a company from the database by id")
    ResponseEntity<?> deleteById(@PathVariable int id);

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a company in the database by id")
    ResponseEntity<?> update(@PathVariable int id, @RequestBody Company company);

}
