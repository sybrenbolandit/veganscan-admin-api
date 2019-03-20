package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.validation.Validated
import io.reactivex.Single
import nl.sybrenbolandit.persistence.model.Ingredient
import nl.sybrenbolandit.persistence.repository.IngredientRepository
import javax.validation.Valid

@Validated
@Controller("/ingredients")
class IngredientController(val ingredientRepository: IngredientRepository) {

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createIngredient(@Body @Valid ingredient: Ingredient): Single<Ingredient> {
        return ingredientRepository.createIngredient(ingredient)
    }

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun ingredientList(): Single<List<Ingredient>> {
        return ingredientRepository.fetchIngredientList()
    }
}