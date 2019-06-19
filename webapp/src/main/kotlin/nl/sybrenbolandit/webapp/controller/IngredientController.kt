package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.validation.Validated
import io.reactivex.Single
import nl.sybrenbolandit.persistence.model.Ingredient
import nl.sybrenbolandit.persistence.repository.IngredientRepository
import javax.validation.Valid

@Validated
@Controller("/ingredients")
class IngredientController(val ingredientRepository: IngredientRepository) {

    @Post("/")
    @Status(HttpStatus.CREATED)
    fun createIngredient(@Body @Valid ingredient: Ingredient): Single<Ingredient> {
        return ingredientRepository.createIngredient(ingredient)
    }

    @Get("/")
    fun ingredientList(): Single<List<Ingredient>> {
        return ingredientRepository.fetchIngredientList()
    }
}
