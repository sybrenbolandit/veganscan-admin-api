package nl.sybrenbolandit.persistence.model

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

data class Ingredient @BsonCreator constructor(
        @param:BsonProperty("name") val name: String,
        @param:BsonProperty("synonyms") val synonyms: List<String>,
        @param:BsonProperty("type") val type: IngredientType,
        @param:BsonProperty("description") val description: String?
)