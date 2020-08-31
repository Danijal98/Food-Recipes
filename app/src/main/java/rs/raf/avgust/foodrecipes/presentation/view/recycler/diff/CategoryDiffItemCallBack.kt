package rs.raf.avgust.foodrecipes.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.avgust.foodrecipes.data.models.Category

class CategoryDiffItemCallBack : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.img == newItem.img
                && oldItem.title == newItem.title
    }

}