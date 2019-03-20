package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.validation.Validated
import io.reactivex.Maybe
import io.reactivex.Single
import nl.sybrenbolandit.persistence.model.Product
import nl.sybrenbolandit.persistence.repository.ProductRepository
import javax.validation.Valid

@Validated
@Controller("/products")
class ProductController(val productRepository: ProductRepository) {

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createProduct(@Body @Valid product: Product): Single<Product> {
        return productRepository.createProduct(product)
    }

    @Get("/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    fun findProduct(barcode: String): Maybe<Product> {
        return productRepository.fetchProduct(barcode.toInt())
    }
}