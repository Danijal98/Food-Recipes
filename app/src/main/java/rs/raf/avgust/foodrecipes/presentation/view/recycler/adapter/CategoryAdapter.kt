package rs.raf.avgust.foodrecipes.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.avgust.foodrecipes.R
import rs.raf.avgust.foodrecipes.data.models.Category
import rs.raf.avgust.foodrecipes.presentation.view.recycler.diff.CategoryDiffItemCallBack
import rs.raf.avgust.foodrecipes.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter (
    categoryDiffItemCallBack: CategoryDiffItemCallBack,
    private val searchAction: (Category) -> Unit
) : ListAdapter<Category, CategoryViewHolder>(categoryDiffItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(containerView){
            searchAction(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}