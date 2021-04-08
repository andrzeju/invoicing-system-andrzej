package pl.futurecollars.invocing.db.memory

import pl.futurecollars.invocing.db.AbstractDatabaseTest
import pl.futurecollars.invocing.db.Database
import pl.futurecollars.invocing.db.InMemoryDatabase

class InMemoryDatabaseIntegrationTest extends AbstractDatabaseTest {

    @Override
    Database getDatabaseInstance() {
        return new InMemoryDatabase()
    }
}