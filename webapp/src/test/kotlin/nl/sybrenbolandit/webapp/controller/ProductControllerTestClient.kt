package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.reactivex.Maybe
import io.reactivex.Single
import nl.sybrenbolandit.persistence.model.Product
import javax.validation.Valid

@Client("/products")
internal interface ProductControllerTestClient {

    @Post("/")
    fun createProduct(@Body @Valid product: Product): Single<Product>

    @Get("/{barcode}")
    fun findProduct(barcode: String): Maybe<Product>

}