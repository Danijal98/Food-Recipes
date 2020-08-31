package rs.raf.avgust.foodrecipes.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.avgust.foodrecipes.R
import rs.raf.avgust.foodrecipes.data.models.Category
import rs.raf.avgust.foodrecipes.presentation.contract.RecipeContract
import rs.raf.avgust.foodrecipes.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.avgust.foodrecipes.presentation.view.recycler.adapter.RecipeAdapter
import rs.raf.avgust.foodrecipes.presentation.view.recycler.adapter.SavedRecipeAdapter
import rs.raf.avgust.foodrecipes.presentation.view.recycler.diff.CategoryDiffItemCallBack
import rs.raf.avgust.foodrecipes.presentation.view.recycler.diff.RecipeDiffItemCallBack
import rs.raf.avgust.foodrecipes.presentation.view.recycler.diff.SavedRecipeDiffItemCallBack
import rs.raf.avgust.foodrecipes.presentation.view.states.RecipeState
import rs.raf.avgust.foodrecipes.presentation.viewmodel.RecipeViewModel
import rs.raf.avgust.foodrecipes.utilities.RecipeFilter
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var search: String

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var savedRecipeAdapter: SavedRecipeAdapter
    private val categoryList: List<Category> = listOf(
        Category(
            "1",
            "https://cdn3.iconfinder.com/data/icons/celebration-and-party-14/64/25-Grill-512.png",
            "Barbecue"
        ),
        Category("2", "https://image.flaticon.com/icons/png/512/575/575426.png", "Breakfast"),
        Category(
            "3",
            "https://cdn3.iconfinder.com/data/icons/meat-and-bbq/100/meat_food-14-512.png",
            "Chicken"
        ),
        Category("4", "https://image.flaticon.com/icons/png/512/123/123281.png", "Beef"),
        Category("5", "https://icon-library.com/images/pancake-icon/pancake-icon-3.jpg", "Brunch"),
        Category(
            "6",
            "https://cdn3.iconfinder.com/data/icons/hotel-service-gray-set-1/100/Untitled-1-09-512.png",
            "Dinner"
        ),
        Category(
            "7",
            "https://cdn0.iconfinder.com/data/icons/valentine-s-day-19/64/Wine_glass-01-512.png",
            "Wine"
        ),
        Category(
            "8",
            "https://www.jing.fm/clipimg/full/16-161907_italian-chef-hat-png-chefs-hat-and-pasta.png",
            "Italian"
        )
    )

    companion object {
        const val MESSAGE_KEY_RECIPE = "recipe"
        var pageNum = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItemSearch = menu?.findItem(R.id.search)

        menuItemSearch?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                categoryRecyclerView.visibility = View.GONE
                savedRecycleView.visibility = View.GONE
                recipeRecyclerView.visibility = View.VISIBLE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                categoryRecyclerView.visibility = View.VISIBLE
                savedRecycleView.visibility = View.GONE
                recipeRecyclerView.visibility = View.GONE
                recipeViewModel.deleteMeals()
                pageNum = 1
                return true
            }

        })

        val searchView: SearchView = menuItemSearch?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(text: String?): Boolean {
                if (text != null) {
                    search = text
                    recipeViewModel.deleteMeals()
                    recipeViewModel.getMeals(RecipeFilter(text))
                    recipeViewModel.fetchMealPage(text, "1")
                }
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return false
            }

        })

        val menuItemCategories = menu.findItem(R.id.categories)
        val menuItemSaved = menu.findItem(R.id.savedMenus)

        menuItemCategories.setOnMenuItemClickListener {
            recipeRecyclerView.visibility = View.GONE
            savedRecycleView.visibility = View.GONE
            categoryRecyclerView.visibility = View.VISIBLE
            recipeViewModel.deleteMeals()
            pageNum = 1
            true
        }

        menuItemSaved.setOnMenuItemClickListener {
            recipeViewModel.getSavedMeals()
            recipeRecyclerView.visibility = View.GONE
            categoryRecyclerView.visibility = View.GONE
            savedRecycleView.visibility = View.VISIBLE
            recipeViewModel.deleteMeals()
            pageNum = 1
            true
        }

        return true
    }

    private fun init() {
        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        recipeViewModel.recipeState.observe(this, Observer {
            renderState(it)
        })
        recipeViewModel.savedRecipeState.observe(this, Observer {
            renderSavedState(it)
        })
    }

    private fun initRecycler(){
        // CategoryAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter(CategoryDiffItemCallBack()) {
            categoryRecyclerView.visibility = View.GONE
            savedRecycleView.visibility = View.GONE
            recipeRecyclerView.visibility = View.VISIBLE
            search = it.title
            recipeViewModel.getMeals(RecipeFilter(it.title))
            recipeViewModel.fetchMealPage(it.title, "1")
        }
        categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.submitList(categoryList)

        // Recipe Adapter
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(RecipeDiffItemCallBack()) {
            val intent = Intent(this, MealDetailsActivity::class.java)
            intent.putExtra(MESSAGE_KEY_RECIPE, it)
            startActivity(intent)
        }
        recipeRecyclerView.adapter = recipeAdapter
        recipeRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    //Toast.makeText(this@MainActivity, "New items fetched", Toast.LENGTH_SHORT).show()
                    recipeViewModel.fetchMealPage(search, (pageNum+1).toString())
                    pageNum+=1
                    recipeAdapter.notifyDataSetChanged()
                }
            }
        })

        // Saved Adapter
        savedRecycleView.layoutManager = LinearLayoutManager(this)
        savedRecipeAdapter = SavedRecipeAdapter(SavedRecipeDiffItemCallBack()) {
            val intent = Intent(this, MealDetailsActivity::class.java)
            intent.putExtra(MESSAGE_KEY_RECIPE, it)
            startActivity(intent)
        }
        savedRecycleView.adapter = savedRecipeAdapter
    }


    private fun renderSavedState(state: RecipeState?) {
        when(state){
            is RecipeState.Success -> {
                savedRecipeAdapter.submitList(state.recipes)
            }
            is RecipeState.Error -> {
                //ERROR
            }
            is RecipeState.DataFetched -> {
                //DATA FETCHED
            }
            is RecipeState.Loading -> {
                //LOADING
            }
        }
    }

    private fun renderState(state: RecipeState) {
        when(state){
            is RecipeState.Success -> {
                recipeAdapter.submitList(state.recipes)
            }
            is RecipeState.Error -> {
                //ERROR
                Timber.e("ERROR")
            }
            is RecipeState.DataFetched -> {
                //ERROR
            }
            is RecipeState.Loading -> {
                Timber.e("Loading")
            }
        }
    }

}