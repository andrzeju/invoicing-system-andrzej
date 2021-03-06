package pl.futurecollars.invoicing

import pl.futurecollars.invoicing.model.Car
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

import java.time.LocalDate

class TestHelpers {

    static company(long id) {
        Company.builder()
                .taxIdentificationNumber("$id")
                .address("ul. Bukowińska 24d/$id 02-703 Warszawa, Polska")
                .name("iCode Trust $id Sp. z o.o")
                .pensionInsurance((BigDecimal.TEN * BigDecimal.valueOf(id)).setScale(2))
                .healthInsurance((BigDecimal.valueOf(100) * BigDecimal.valueOf(id)).setScale(2))
                .build()
    }

    static product(long id) {
        InvoiceEntry.builder()
                .description("Programming course $id")
                .quantity(1.50)
                .price(BigDecimal.valueOf(id * 1000).setScale(2))
                .vatValue(BigDecimal.valueOf(id * 1000 * 0.08).setScale(2))
                .vatRate(Vat.VAT_8)
                .carRelatedExpense(id % 2 == 0 ? null :
                        Car.builder()
                                .registration("XYZ")
                                .includingPersonalUse(false)
                                .build()
                )
                .build()
    }

    static invoice(long id) {
        Invoice.builder()
                .date(LocalDate.now())
                .number("12/2021/$id")
                .buyer(company(id + 10))
                .seller(company(id))
                .entries((1..id).collect({ product(it) }))
                .build()
    }

    static Invoice resetIds(Invoice invoice) {
        invoice.getBuyer().id = null
        invoice.getSeller().id = null
        invoice.entries.forEach {
            it.id = null
            it.carRelatedExpense?.id = null
        }
        invoice
    }

    static List<Invoice> resetIds(List<Invoice> invoices) {
        invoices.forEach { invoice -> resetIds(invoice) }
    }
}
