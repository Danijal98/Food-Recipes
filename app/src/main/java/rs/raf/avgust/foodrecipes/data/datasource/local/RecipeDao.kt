package rs.raf.avgust.foodrecipes.data.datasource.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avgust.foodrecipes.data.models.IngredientsEntity
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.data.models.RecipeEntity
import rs.raf.avgust.foodrecipes.data.models.SavedRecipeEntity

@Dao
abstract class RecipeDao {

    @Insert( onConflict =  OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<RecipeEntity>): Completable

    @Insert ( onConflict = OnConflictStrategy.IGNORE )
    abstract fun saveMeal(meal: SavedRecipeEntity): Completable

    @Query("SELECT * FROM savedRecipes")
    abstract fun getSavedMeals(): Observable<List<SavedRecipeEntity>>

    @Query("SELECT * FROM recipe")
    abstract fun getMeals(): Observable<List<RecipeEntity>>

    @Query("DELETE FROM recipe")
    abstract fun deleteAll(): Completable

    @Query("SELECT * FROM ingredient WHERE recipe_id == :meal_id")
    abstract fun getIngredientsForMeal(meal_id: String): Observable<IngredientsEntity>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertIngredient(ingredient: IngredientsEntity): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<RecipeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

}