package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import nl.sybrenbolandit.persistence.model.Ingredient

@Controller("/ingredients")
class IngredientController {

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return "Hello World"
    }

    @Get("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    fun ingredient(name: String): Ingredient {
        return Ingredient(name)
    }
}