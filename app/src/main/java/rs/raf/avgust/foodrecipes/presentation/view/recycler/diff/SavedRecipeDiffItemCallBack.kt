package rs.raf.avgust.foodrecipes.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.avgust.foodrecipes.data.models.Recipe

class SavedRecipeDiffItemCallBack : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return  oldItem.title == newItem.title
                && oldItem.category == newItem.category
                && oldItem.date == newItem.date
    }

}