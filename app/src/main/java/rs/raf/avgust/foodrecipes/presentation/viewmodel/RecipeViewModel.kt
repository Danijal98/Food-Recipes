package rs.raf.avgust.foodrecipes.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.data.models.Resource
import rs.raf.avgust.foodrecipes.data.models.SavedRecipeEntity
import rs.raf.avgust.foodrecipes.data.repositories.RecipeRepository
import rs.raf.avgust.foodrecipes.presentation.contract.RecipeContract
import rs.raf.avgust.foodrecipes.presentation.view.states.IngredientState
import rs.raf.avgust.foodrecipes.presentation.view.states.RecipeState
import rs.raf.avgust.foodrecipes.utilities.RecipeFilter
import timber.log.Timber
import java.util.*

class RecipeViewModel (
    private val recipeRepository: RecipeRepository
): ViewModel(), RecipeContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val recipeState: MutableLiveData<RecipeState> = MutableLiveData()
    override val savedRecipeState: MutableLiveData<RecipeState> = MutableLiveData()
    override val ingredientsState: MutableLiveData<IngredientState> = MutableLiveData()
    private val publishSubject: PublishSubject<RecipeFilter> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .switchMap {
                recipeRepository
                    .getMeals(it.meal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    recipeState.value = RecipeState.Success(it)
                },
                {
                    recipeState.value = RecipeState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchMealPage(meal: String, page: String) {
        val subscription = recipeRepository
            .fetchPage(meal, page)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> recipeState.value = RecipeState.Loading
                        is Resource.Success -> recipeState.value = RecipeState.DataFetched
                        is Resource.Error -> recipeState.value = RecipeState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    recipeState.value = RecipeState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMeals(filter: RecipeFilter) {
        publishSubject.onNext(filter)
    }

    override fun deleteMeals() {
        val subscription = recipeRepository
            .deleteMeals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.d("Cash deleted")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchIngredients(meal_id: String) {
        val subscription = recipeRepository
            .fetchIngredient(meal_id)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> ingredientsState.value = IngredientState.Loading
                        is Resource.Success -> ingredientsState.value = IngredientState.DataFetched
                        is Resource.Error -> ingredientsState.value = IngredientState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    ingredientsState.value = IngredientState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getIngredients(meal_id: String) {
        val subscription = recipeRepository
            .getIngredient(meal_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    ingredientsState.value = IngredientState.Success(it)
                },
                {
                    ingredientsState.value = IngredientState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getSavedMeals() {
        val subscription = recipeRepository
            .getSavedMeals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    savedRecipeState.value = RecipeState.Success(it)
                },
                {
                    savedRecipeState.value = RecipeState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun saveMeal(recipe: Recipe, category: String, date: Date) {
        val subscription = recipeRepository
            .saveMeal(
                SavedRecipeEntity(
                    id = recipe.id,
                    title = recipe.title,
                    publisher = recipe.publisher,
                    recipe_id = recipe.recipe_id,
                    category = category,
                    date = date
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.d("Meal saved")
                },
                {
                    savedRecipeState.value = RecipeState.Error("Error happened while updating data in db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}