package nl.sybrenbolandit.persistence.model

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

data class Product @BsonCreator constructor(
        @param:BsonProperty("name") val name: String,
        @param:BsonProperty("barcode") val barcode: String,
        @param:BsonProperty("ingredients") val ingredients: List<Ingredient>
)