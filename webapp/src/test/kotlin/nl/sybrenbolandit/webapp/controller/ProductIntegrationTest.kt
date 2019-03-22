package nl.sybrenbolandit.webapp.controller

import com.mongodb.reactivestreams.client.MongoClient
import io.micronaut.configuration.mongo.reactive.MongoSettings
import io.micronaut.context.ApplicationContext
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.runtime.server.EmbeddedServer
import io.reactivex.Flowable
import nl.sybrenbolandit.persistence.config.DataSourceConfig
import nl.sybrenbolandit.persistence.model.Ingredient
import nl.sybrenbolandit.persistence.model.IngredientType
import nl.sybrenbolandit.persistence.model.Product
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertNull
import org.junit.jupiter.api.Assertions.assertEquals

class ProductIntegrationTest : Spek({

    describe("Product controller") {
        val server = ApplicationContext.run(
                EmbeddedServer::class.java,
                mapOf(
                        "micronaut.server.port" to -1,
                        MongoSettings.MONGODB_URI to "mongodb://localhost:" + SocketUtils.findAvailableTcpPort()
                )
        )
        val client = server.applicationContext.getBean(ProductControllerTestClient::class.java)

        it("should create a new product") {
            val newProduct = createProduct(111)
            val response: Product = client.createProduct(newProduct).blockingGet()

            assertEquals(newProduct, response)
        }

        it("should retrieve created product by barcode") {
            var response = client.findProduct("111").blockingGet()
            assertNull(response)

            val newProduct = createProduct(111)
            client.createProduct(newProduct).blockingGet()

            response = client.findProduct("111").blockingGet()
            assertEquals(newProduct, response)
        }

        afterEachTest {
            val mongoClient: MongoClient = server.applicationContext.getBean(MongoClient::class.java)
            val config: DataSourceConfig = server.applicationContext.getBean(DataSourceConfig::class.java)

            Flowable.fromPublisher(
                    mongoClient.getDatabase(config.databaseName)
                            .drop()
            ).blockingFirst()
        }

        afterGroup {
            server.stop()
        }
    }
})

private fun createProduct(barcode: Int): Product {
    return Product("product", barcode, listOf(Ingredient("sdf", listOf("sdf"), IngredientType.MAYBE_VEGAN, null)))
}

