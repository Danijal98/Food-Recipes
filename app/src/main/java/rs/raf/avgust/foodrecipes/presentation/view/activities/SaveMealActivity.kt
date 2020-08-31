package rs.raf.avgust.foodrecipes.presentation.view.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_save_meal.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.avgust.foodrecipes.R
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.presentation.contract.RecipeContract
import rs.raf.avgust.foodrecipes.presentation.viewmodel.RecipeViewModel
import java.text.SimpleDateFormat
import java.util.*

class SaveMealActivity : AppCompatActivity(R.layout.activity_save_meal), OnDateSetListener {

    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var recipeToSave: Recipe
    lateinit var selectedDate: Date

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeToSave = intent.getParcelableExtra(MainActivity.MESSAGE_KEY_RECIPE)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun init() {
        initView()
        initListeners()
    }

    private fun initView() {
        mealTitle.text = recipeToSave.title
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        selectedDate = date
        dateBtn.text = formatter.format(date)

        // Spinner
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initListeners() {
        addToMenuBtn.setOnClickListener {
            val category: String = categorySpinner.selectedItem.toString()
            recipeViewModel.saveMeal(recipeToSave, category, selectedDate)
            Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
        }

        dateBtn.setOnClickListener {
            DatePickerDialog(
                this,
                this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    private fun DatePicker.getDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.time
    }

    override fun onDateSet(dp: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val myCalendar = Calendar.getInstance()
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val date = dp?.getDate() ?: myCalendar.time
        selectedDate = date

        val formatter = SimpleDateFormat("dd-MM-yyyy")
        dateBtn.text = formatter.format(date)
    }

}