package rs.raf.avgust.foodrecipes.data.models

import com.squareup.moshi.JsonClass
import rs.raf.avgust.foodrecipes.data.models.recipes.Recipe

@JsonClass(generateAdapter = true)
data class RecipeResponse(
    val count: String,
    val recipes: List<Recipe>
)