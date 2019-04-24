package nl.sybrenbolandit.webapp.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.http.hateos.JsonError
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

    @Error(global = true)
    fun error(request: HttpRequest<Any>, e: Throwable): HttpResponse<JsonError> =
            when (e) {
                is NumberFormatException -> HttpResponse.notFound()
                else -> HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
            }
}