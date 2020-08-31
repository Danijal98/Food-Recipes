package rs.raf.avgust.foodrecipes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class IngredientsEntity(
    @PrimaryKey val id: String,
    val ingredients: List<String>,
    val recipe_id: String
)