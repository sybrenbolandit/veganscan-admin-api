package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/ingredients")
class IngredientController {

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return "Hello World"
    }
}