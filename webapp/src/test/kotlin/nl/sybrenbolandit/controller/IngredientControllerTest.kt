package nl.sybrenbolandit.controller

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import junit.framework.TestCase.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object IngredientControllerTest: Spek({
    describe("HelloController Suite") {
        val embeddedServer : EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
        val client : HttpClient = HttpClient.create(embeddedServer.url)

        it("test /hello responds Hello World") {
            val rsp : String = client.toBlocking().retrieve("/hello")
            assertEquals(rsp, "Hello World")
        }

        afterGroup {
            client.close()
            embeddedServer.close()
        }
    }
})