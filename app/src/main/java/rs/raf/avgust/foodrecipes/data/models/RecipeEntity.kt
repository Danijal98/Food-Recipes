package rs.raf.avgust.foodrecipes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val page: String,
    val title: String,
    val imageUrl: String,
    val socialRank: String,
    val publisher: String,
    val recipe_id: String
)