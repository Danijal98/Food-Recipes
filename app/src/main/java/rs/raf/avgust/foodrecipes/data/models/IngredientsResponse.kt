package rs.raf.avgust.foodrecipes.data.models

import com.squareup.moshi.JsonClass
import rs.raf.avgust.foodrecipes.data.models.recipes.RecipeIngredients

@JsonClass(generateAdapter = true)
data class IngredientsResponse(
    val recipe: RecipeIngredients
)