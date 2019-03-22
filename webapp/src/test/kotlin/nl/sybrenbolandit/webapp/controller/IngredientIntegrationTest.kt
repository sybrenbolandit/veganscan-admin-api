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
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertEquals

class IngredientIntegrationTest : Spek({

    describe("Ingredient controller") {
        val server = ApplicationContext.run(
                EmbeddedServer::class.java,
                mapOf(
                        "micronaut.server.port" to -1,
                        MongoSettings.MONGODB_URI to "mongodb://localhost:" + SocketUtils.findAvailableTcpPort()
                )
        )
        val client = server.applicationContext.getBean(IngredientControllerTestClient::class.java)

        it("should create a new ingredient") {
            val newIngredient = createIngredient("Apple")
            val response: Ingredient = client.createIngredient(newIngredient).blockingGet()

            assertEquals(newIngredient, response)
        }

        it("should retrieve created ingredients") {
            var response: List<Ingredient> = client.ingredientList().blockingGet()
            assertEquals(0, response.size)

            val newIngredient = createIngredient("Apple")
            client.createIngredient(newIngredient).blockingGet()

            response = client.ingredientList().blockingGet()
            assertEquals(1, response.size)
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

private fun createIngredient(name: String): Ingredient {
    return Ingredient(name, listOf(name), IngredientType.MAYBE_VEGAN, null)
}

