package pl.futurecollars.invoicing.db.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;

public class SqlDatabase implements Database {

    private final JdbcTemplate jdbcTemplate;

    public SqlDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Invoice invoice) {
        int buyerId = insertCompany(invoice.getBuyer());
        int sellerId = insertCompany(invoice.getSeller());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection ->
        {
            PreparedStatement ps = connection.prepareStatement(
                "insert into public.invoice (date, invoice_number, buyer, seller) values (?, ?, ?, ?);",
                new String[] {"id"});
            ps.setDate(1, Date.valueOf("date"));
            ps.setString(2, invoice.getNumber());
            ps.setLong(3, buyerId);
            ps.setLong(4, sellerId);
            return ps;
        }, keyHolder);

        int invoiceId = keyHolder.getKey().intValue();
        return invoiceId;
    }

    @Override
    public Optional<Invoice> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Invoice> getAll() {
        return jdbcTemplate.query(
            "select i.date, i.invoice_number, c1.name as seller_name, c2.name as buyer_name from invoice i " +
            "inner join company c1 on i.seller = c1.id " +
            "inner join company c2 on i.buyer = c2.id" , (rs, rowNr) ->
            Invoice.builder()
                .number(rs.getString("invoice_number"))
                .date(rs.getDate("date").toLocalDate())
                .buyer(Company.builder().name(rs.getString("buyer_name")).build())
                .seller(Company.builder().name(rs.getString("seller_name")).build())
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

    private int insertCompany(Company buyer) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "insert into company (name, address, tax_identification_number, health_insurance, pension_insurance) values (?, ?, ?, ?, ?);",
                new String[] {"id"});
            ps.setString(1, buyer.getName());
            ps.setString(2, buyer.getAddress());
            ps.setString(3, buyer.getTaxIdentificationNumber());
            ps.setBigDecimal(4, buyer.getHealthInsurance());
            ps.setBigDecimal(5, buyer.getPensionInsurance());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
