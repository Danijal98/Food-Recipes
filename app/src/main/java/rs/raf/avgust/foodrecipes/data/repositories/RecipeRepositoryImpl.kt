package rs.raf.avgust.foodrecipes.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.avgust.foodrecipes.data.datasource.local.RecipeDao
import rs.raf.avgust.foodrecipes.data.datasource.remote.RecipeService
import rs.raf.avgust.foodrecipes.data.models.*
import rs.raf.avgust.foodrecipes.data.models.recipes.RecipeIngredients
import timber.log.Timber
import java.util.*

class RecipeRepositoryImpl (
    private val localDataSource: RecipeDao,
    private val remoteDataSource: RecipeService
): RecipeRepository {

    override fun fetchPage(meal: String, page: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getPage(meal, page)
            .doOnNext {
                Timber.e("Fethcing page...")

                val recipes = it.recipes.map {
                    RecipeEntity(
                        id = it._id,
                        title = it.title,
                        socialRank = it.social_rank,
                        imageUrl = it.image_url,
                        page = page,
                        recipe_id = it.recipe_id,
                        publisher = it.publisher
                    )
                }

                localDataSource.insertAll(recipes).blockingAwait()

            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getMeals(meal: String): Observable<List<Recipe>> {
        return localDataSource
            .getMeals()
            .map {
                it.map {
                    Recipe(
                        id = it.id,
                        page = it.page,
                        title = it.title,
                        imageUrl = it.imageUrl,
                        socialRank = it.socialRank,
                        publisher = it.publisher,
                        recipe_id = it.recipe_id,
                        ingredients = listOf()
                    )
                }
            }
    }

    override fun saveMeal(meal: SavedRecipeEntity): Completable {
        return localDataSource.saveMeal(meal)
    }

    override fun getSavedMeals(): Observable<List<Recipe>> {
        return localDataSource
            .getSavedMeals()
            .map {
                it.map {
                    Recipe(
                        id = it.id,
                        title = it.title,
                        publisher = it.publisher,
                        recipe_id = it.recipe_id,
                        category = it.category,
                        date = it.date,
                        ingredients = listOf()
                    )
                }
            }
    }

    override fun fetchIngredient(meal_id: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getIngredients(meal_id)
            .doOnNext {
                Timber.e("Fetching ingredients...")

                val recipeIngredients = it.recipe
                val ingredientEntity = IngredientsEntity (
                    id = recipeIngredients._id,
                    ingredients = recipeIngredients.ingredients,
                    recipe_id = recipeIngredients.recipe_id
                )
                localDataSource.insertIngredient(ingredientEntity).blockingAwait()
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getIngredient(meal_id: String): Observable<Ingredients> {
        Timber.e("Getting ingredients...")
        return localDataSource
            .getIngredientsForMeal(meal_id)
            .map {
                Ingredients(
                    id = it.id,
                    ingredients = it.ingredients,
                    recipe_id = it.recipe_id
                )
            }
    }

    override fun deleteMeals(): Completable {
        return localDataSource
            .deleteAll()
    }

}