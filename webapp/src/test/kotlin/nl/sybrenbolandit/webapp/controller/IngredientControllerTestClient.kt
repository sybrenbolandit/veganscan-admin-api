package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single
import nl.sybrenbolandit.persistence.model.Ingredient
import javax.validation.Valid

@Client("/ingredients")
internal interface IngredientControllerTestClient {

    @Post("/")
    fun createIngredient(@Body @Valid ingredient: Ingredient): Single<Ingredient>

    @Get("/")
    fun ingredientList(): Single<List<Ingredient>>
}