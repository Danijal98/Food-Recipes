package rs.raf.avgust.foodrecipes.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_save_meal.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.avgust.foodrecipes.R
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.presentation.contract.RecipeContract
import rs.raf.avgust.foodrecipes.presentation.viewmodel.RecipeViewModel
import java.util.*

class SaveMealActivity : AppCompatActivity(R.layout.activity_save_meal) {

    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var recipeToSave: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeToSave = intent.getParcelableExtra(MainActivity.MESSAGE_KEY_RECIPE)
        init()
    }

    private fun init() {
        initView()
        initListeners()
    }

    private fun initView() {
        mealTitle.text = recipeToSave.title
        val spinner = categorySpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    private fun initListeners() {
        addToMenuBtn.setOnClickListener {
            val category: String = categorySpinner.selectedItem.toString()
            val date: Date = datePicker.getDate()
            recipeViewModel.saveMeal(recipeToSave, category, date)
            Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
        }
    }

    fun DatePicker.getDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.time
    }

}