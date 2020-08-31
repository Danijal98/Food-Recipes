package rs.raf.avgust.foodrecipes.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.avgust.foodrecipes.R
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.presentation.view.recycler.diff.SavedRecipeDiffItemCallBack
import rs.raf.avgust.foodrecipes.presentation.view.recycler.viewholder.SavedViewHolder

class SavedRecipeAdapter (
    recipeDiffItemCallBack: SavedRecipeDiffItemCallBack,
    private val detailsAction: (Recipe) -> Unit
) : ListAdapter<Recipe, SavedViewHolder>(recipeDiffItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.saved_meal_item, parent, false)
        return SavedViewHolder(containerView) {
            detailsAction(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}