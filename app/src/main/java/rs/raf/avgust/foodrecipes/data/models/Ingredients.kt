package rs.raf.avgust.foodrecipes.data.models

data class Ingredients(
    val id: String,
    val ingredients: List<String>,
    val recipe_id: String
)