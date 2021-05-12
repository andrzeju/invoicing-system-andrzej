package pl.futurecollars.invoicing.db.sql;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class SqlDatabase implements Database {

    private final JdbcTemplate jdbcTemplate;

    public SqlDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Invoice invoice) {
        return jdbcTemplate.update("insert into public.invoice (date, invoice_number, buyer, seller) values ('2021-05-12', '12/2021', 1, 2);");
    }

    @Override
    public Optional<Invoice> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Invoice> getAll() {
        return jdbcTemplate.query("select * from invoice" , (rs, rowNr) ->
            Invoice.builder()
                .number(rs.getString("invoice_number"))
                .date(rs.getDate("date").toLocalDate())
                .build()
        );
    }

    @Override
    public Optional<Invoice> update(int id, Invoice updatedInvoice) {
        return Optional.empty();
    }

    @Override
    public Optional<Invoice> delete(int id) {
        return Optional.empty();
    }
}
