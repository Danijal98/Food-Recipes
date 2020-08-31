package rs.raf.avgust.foodrecipes.data.datasource.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.avgust.foodrecipes.data.models.IngredientsResponse
import rs.raf.avgust.foodrecipes.data.models.RecipeResponse

interface RecipeService {

    @GET("search?")
    fun getPage(@Query("q")meal: String, @Query("page")page: String): Observable<RecipeResponse>

    @GET("get")
    fun getIngredients(@Query("rId")meal_id: String): Observable<IngredientsResponse>

}