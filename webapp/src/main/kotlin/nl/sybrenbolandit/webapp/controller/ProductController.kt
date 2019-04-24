package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.validation.Validated
import io.reactivex.Maybe
import io.reactivex.Single
import nl.sybrenbolandit.persistence.model.Product
import nl.sybrenbolandit.persistence.repository.ProductRepository
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Validated
@Controller("/products")
class ProductController(val productRepository: ProductRepository) {

    @Post("/")
    @Status(HttpStatus.CREATED)
    fun createProduct(@Body @Valid product: Product): Single<Product> {
        return productRepository.createProduct(product)
    }

    @Get("/{barcode}")
    fun findProduct(@NotBlank barcode: String): Maybe<Product> {
        return productRepository.fetchProduct(barcode)
    }
}