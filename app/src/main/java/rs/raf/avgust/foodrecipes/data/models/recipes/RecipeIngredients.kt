package rs.raf.avgust.foodrecipes.data.models.recipes

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeIngredients(
    val _id: String,
    val ingredients: List<String>,
    @PrimaryKey val recipe_id: String
)