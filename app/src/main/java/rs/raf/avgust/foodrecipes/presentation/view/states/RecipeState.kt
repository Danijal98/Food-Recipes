package rs.raf.avgust.foodrecipes.presentation.view.states

import rs.raf.avgust.foodrecipes.data.models.Recipe

sealed class RecipeState {
    object Loading: RecipeState()
    object DataFetched: RecipeState()
    data class Success(val recipes: List<Recipe>): RecipeState()
    data class Error(val message: String): RecipeState()
}