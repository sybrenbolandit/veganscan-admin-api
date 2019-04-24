package nl.sybrenbolandit.persistence.repository

import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import nl.sybrenbolandit.persistence.config.DataSourceConfig
import nl.sybrenbolandit.persistence.model.Product
import org.litote.kmongo.reactivestreams.getCollection
import javax.inject.Singleton

@Singleton
class ProductRepository(private val configuration: DataSourceConfig, private val mongoClient: MongoClient) {

    fun fetchProduct(barcode: String): Maybe<Product> {
       return Flowable.fromPublisher(
                getCollection()
                        .find(eq("barcode", barcode))
                        .limit(1)
        ).firstElement()
    }

    fun createProduct(product: Product): Single<Product> {
        return Single.fromPublisher(
                getCollection()
                        .insertOne(product)
        ).map { product }
    }

    private fun getCollection(): MongoCollection<Product> {
        return mongoClient
                .getDatabase(configuration.databaseName)
                .getCollection()
    }
}
