package pl.futurecollars.invoicing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.futurecollars.invoicing.TestHelpers
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.utils.JsonService
import spock.lang.Specification
import spock.lang.Stepwise

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Stepwise
class InvoiceControllerStepwiseTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private JsonService jsonService

    def invoice = TestHelpers.invoice(1)
    private LocalDate updatedDate = LocalDate.of(2021, 04, 15)

    def "empty array is returned when no invoices were added"() {
        when:
        def response = mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString
        then:
        response == "[]"
    }

    def "add a single invoice"() {
        given:
        def invoiceAsJson = jsonService.toJson(invoice)
        when:
        def postContent = mockMvc.perform(
                post("/invoices").content(invoiceAsJson)
                .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString
        then:
        postContent == "1"
    }

    def "one invoice is returned from the list"() {
        given:
        def testInvoice = invoice
        testInvoice.id = 1

        when:
        def singleInvoiceList = mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString
        def resultInvoices = jsonService.toObject(singleInvoiceList, Invoice[])

        then:
        resultInvoices.size() == 1
        resultInvoices[0].toString() == invoice.toString()
    }

    def "invoice is returned correctly when getting by id"() {
        given:
        def expectedInvoice = invoice
        expectedInvoice.id = 1

        when:
        def response = mockMvc.perform(get("/invoices/1"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        def invoices = jsonService.toObject(response, Invoice)
        then:
        invoices == expectedInvoice
    }

    def "invoice date can be modified"() {
        given:
        def modifiedInvoice = invoice
        modifiedInvoice.id = 1
        modifiedInvoice.date = updatedDate

        def invoiceAsJson = jsonService.toJson(modifiedInvoice)

        when:
        def result = mockMvc.perform(
                put("/invoices/1")
                        .content(invoiceAsJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )

        then:
                result.andExpect(status().isNoContent())
    }

    def "updated invoice is returned correctly when getting by id"() {
        given:
        def expectedInvoice = invoice
        expectedInvoice.id = 1
        expectedInvoice.date = updatedDate

        when:
        def response = mockMvc.perform(get("/invoices/1"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        def invoices = jsonService.toObject(response, Invoice)
        then:
        invoices == expectedInvoice
    }


    def "invoice can be deleted"() {
        expect:
        mockMvc.perform(delete("/invoices/1")).andExpect(status().isNoContent())
        and:
        mockMvc.perform(delete("/invoices/1")).andExpect(status().isNotFound())

    }
}
