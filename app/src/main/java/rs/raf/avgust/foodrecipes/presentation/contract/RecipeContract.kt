package rs.raf.avgust.foodrecipes.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.avgust.foodrecipes.data.models.Ingredients
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.presentation.view.states.IngredientState
import rs.raf.avgust.foodrecipes.presentation.view.states.RecipeState
import rs.raf.avgust.foodrecipes.utilities.RecipeFilter
import java.util.*

interface RecipeContract {

    interface ViewModel {

        val recipeState: LiveData<RecipeState>
        val ingredientsState: LiveData<IngredientState>
        val savedRecipeState: LiveData<RecipeState>

        // Meals
        fun fetchMealPage(meal: String, page: String)
        fun getMeals(filter: RecipeFilter)
        fun getSavedMeals()
        fun saveMeal(recipe: Recipe, category: String, date: Date)
        fun deleteMeals()

        // Ingredients
        fun fetchIngredients(meal_id: String)
        fun getIngredients(meal_id: String)

    }

}