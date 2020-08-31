package rs.raf.avgust.foodrecipes.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avgust.foodrecipes.data.models.*

interface RecipeRepository {

    fun fetchPage(meal: String, page: String): Observable<Resource<Unit>>
    fun getMeals(meal: String): Observable<List<Recipe>>
    fun saveMeal(meal: SavedRecipeEntity): Completable
    fun getSavedMeals(): Observable<List<Recipe>>
    fun fetchIngredient(meal_id: String): Observable<Resource<Unit>>
    fun getIngredient(meal_id: String): Observable<Ingredients>
    fun deleteMeals(): Completable

}