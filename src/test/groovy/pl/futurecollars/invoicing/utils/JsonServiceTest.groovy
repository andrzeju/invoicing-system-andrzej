package pl.futurecollars.invoicing.utils

import pl.futurecollars.invoicing.model.InvoiceEntry
import spock.lang.Specification

class JsonServiceTest extends Specification {

    def "expect json processing exception when mapping to object"() {
        given:
        JsonService service = new JsonService();

        when:
        service.toObject("corrupted json ////", InvoiceEntry.class)

        then:
        RuntimeException exception = thrown()
    }

}
