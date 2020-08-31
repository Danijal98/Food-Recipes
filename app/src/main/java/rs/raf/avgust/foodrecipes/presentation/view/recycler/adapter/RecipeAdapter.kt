package rs.raf.avgust.foodrecipes.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.avgust.foodrecipes.R
import rs.raf.avgust.foodrecipes.data.models.Recipe
import rs.raf.avgust.foodrecipes.presentation.view.recycler.diff.RecipeDiffItemCallBack
import rs.raf.avgust.foodrecipes.presentation.view.recycler.viewholder.RecipeViewHolder

class RecipeAdapter (
    recipeDiffItemCallBack: RecipeDiffItemCallBack,
    private val detailsAction: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeViewHolder>(recipeDiffItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(containerView) {
            detailsAction(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}