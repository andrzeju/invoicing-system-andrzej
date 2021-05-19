package pl.futurecollars.invoicing.utils

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class FilesServiceTest extends Specification {

    private final FilesService filesService = new FilesService()
    private final Path path = File.createTempFile('test-invoices', '.txt').toPath()

    def "new line is added to the file"() {
        setup:
        def testLine = "{name: 'invoicetest'}"

        expect:
        [] == Files.readAllLines(path)

        when:
        filesService.appendLineToFile(path, testLine)

        then:
        [testLine] == Files.readAllLines(path)

        when:
        filesService.appendLineToFile(path, testLine)

        then:
        [testLine, testLine] == Files.readAllLines(path)
    }

    def "new line is inserted to the file"() {
        expect:
        [] == Files.readAllLines(path)

        when:
        filesService.writeToFile(path, "1")

        then:
        ["1"] == Files.readAllLines(path)

        when:
        filesService.writeToFile(path, "2")

        then:
        ["2"] == Files.readAllLines(path)
    }

    def "list is added to the file"() {
        given:
        def digits = ['1', '2', '3']
        def letters = ['a', 'b', 'c']

        expect:
        [] == Files.readAllLines(path)

        when:
        filesService.writeLinesToFile(path, digits)

        then:
        digits == Files.readAllLines(path)

        when:
        filesService.writeLinesToFile(path, letters)

        then:
        letters == Files.readAllLines(path)
    }

    def "line is correctly read from file"() {
        setup:
        def lines = List.of("line 1", "line 2", "line 3")
        Files.write(path, lines)

        expect:
        lines == filesService.readAllLines(path)
    }

    def "empty file returns empty collection"() {
        expect:
        [] == filesService.readAllLines(path)
    }

}
