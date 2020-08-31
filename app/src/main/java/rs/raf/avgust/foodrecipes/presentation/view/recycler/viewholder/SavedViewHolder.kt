package rs.raf.avgust.foodrecipes.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.saved_meal_item.*
import rs.raf.avgust.foodrecipes.data.models.Recipe
import java.text.SimpleDateFormat
import java.util.*

class SavedViewHolder(
    override val containerView: View,
    detailsAction: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { detailsAction(adapterPosition) }
    }

    fun bind(recipe: Recipe) {
        mealTitle.text = recipe.title
        mealCategory.text = recipe.category
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date: Date = recipe.date
        mealDate.text = formatter.format(date)
    }

}