package rs.raf.avgust.foodrecipes.presentation.view.states

import rs.raf.avgust.foodrecipes.data.models.Ingredients

sealed class IngredientState {
    object Loading: IngredientState()
    object DataFetched: IngredientState()
    data class Success(val ingredients: Ingredients): IngredientState()
    data class Error(val message: String): IngredientState()
}