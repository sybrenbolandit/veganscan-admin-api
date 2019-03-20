package nl.sybrenbolandit.persistence.repository

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.reactivex.Flowable
import io.reactivex.Single
import nl.sybrenbolandit.persistence.config.DataSourceConfig
import nl.sybrenbolandit.persistence.model.Ingredient
import org.litote.kmongo.reactivestreams.getCollection

import javax.inject.Singleton

@Singleton
class IngredientRepository(private val configuration: DataSourceConfig, private val mongoClient: MongoClient) {

    fun fetchIngredientList(): Single<List<Ingredient>> {
        return Flowable.fromPublisher(
                getCollection()
                        .find()
        ).toList()
    }

    fun createIngredient(ingredient: Ingredient): Single<Ingredient> {
        return Single.fromPublisher(
                getCollection()
                        .insertOne(ingredient)
        ).map { ingredient }
    }

    private fun getCollection(): MongoCollection<Ingredient> {
        return mongoClient
                .getDatabase(configuration.databaseName)
                .getCollection()
    }
}
