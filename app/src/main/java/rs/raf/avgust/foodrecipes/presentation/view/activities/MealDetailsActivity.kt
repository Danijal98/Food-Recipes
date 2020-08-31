package rs.raf.avgust.foodrecipes.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_meal_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.avgust.foodrecipes.R
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.presentation.contract.RecipeContract
import rs.raf.avgust.foodrecipes.presentation.view.states.IngredientState
import rs.raf.avgust.foodrecipes.presentation.view.states.RecipeState
import rs.raf.avgust.foodrecipes.presentation.viewmodel.RecipeViewModel
import timber.log.Timber
import kotlin.math.roundToInt

class MealDetailsActivity : AppCompatActivity(R.layout.activity_meal_details) {

    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipe = intent.getParcelableExtra(MainActivity.MESSAGE_KEY_RECIPE)
        // Get ingredients for this meal
        recipeViewModel.getIngredients(recipe.recipe_id)
        recipeViewModel.fetchIngredients(recipe.recipe_id)
        init()
    }

    private fun init() {
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        if(recipe.imageUrl.isNotBlank()) {
            Picasso
                .get()
                .load(recipe.imageUrl)
                .into(recipeImage)
        }else {
            recipeImage.visibility = View.GONE
        }
        recipeTitle.text = recipe.title
        if (recipe.socialRank.isNotBlank())
            recipeSocialRank.text = recipe.socialRank.toDouble().roundToInt().toString()
        else recipeSocialRank.text = recipe.category
    }

    private fun initObservers() {
        recipeViewModel.ingredientsState.observe(this, Observer {
            renderIngredientState(it)
        })
    }

    private fun initListeners() {
        recipeSaveBtn.setOnClickListener {
            val intent = Intent(this, SaveMealActivity::class.java)
            intent.putExtra(MainActivity.MESSAGE_KEY_RECIPE, recipe)
            startActivity(intent)
        }
    }


    private fun renderIngredientState(state: IngredientState) {
        when(state){
            is IngredientState.Success -> {
                //Timber.e("Success")
                val ingredients = state.ingredients
                Timber.e("Rendering")
                if (ingredientsContainer.childCount == 0) {
                    for (ingredient in ingredients.ingredients){
                        val textView = TextView(this)
                        textView.text = ingredient
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        textView.layoutParams = params
                        ingredientsContainer.addView(textView)
                    }
                }
            }
            is IngredientState.Error -> {
                //Timber.e("Error")
            }
            is IngredientState.DataFetched -> {
                //Timber.e("Data Fetched")
            }
            is IngredientState.Loading -> {
                //Timber.e("Loading")
            }
        }
    }

}