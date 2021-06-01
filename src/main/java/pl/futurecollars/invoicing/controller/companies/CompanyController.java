package pl.futurecollars.invoicing.controller.companies;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.service.CompanyService;

@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    @Override
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @Override
    public long add(Company company) {
        return companyService.save(company);
    }

    @Override
    public ResponseEntity<Company> getById(int id) {
        return companyService.getById(id)
            .map(item -> ResponseEntity.ok().body(item))
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> deleteById(int id) {
        return companyService.delete(id)
            .map(item -> ResponseEntity.noContent().build())
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> update(int id, Company company) {
        return companyService.update(id, company)
            .map(item -> ResponseEntity.noContent().build())
            .orElse(ResponseEntity.notFound().build());
    }
}
