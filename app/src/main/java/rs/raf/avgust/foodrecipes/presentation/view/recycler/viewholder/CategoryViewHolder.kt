package rs.raf.avgust.foodrecipes.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.category_item.*
import rs.raf.avgust.foodrecipes.data.models.Category

class CategoryViewHolder(
    override val containerView: View,
    searchAction: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener{ searchAction(adapterPosition) }
    }

    fun bind(category: Category) {
        Picasso
            .get()
            .load(category.img)
            .into(categoryImage)
        categoryTitle.text = category.title
    }

}