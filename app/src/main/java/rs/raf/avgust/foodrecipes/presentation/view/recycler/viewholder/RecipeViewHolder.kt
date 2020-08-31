package rs.raf.avgust.foodrecipes.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_item.*
import rs.raf.avgust.foodrecipes.data.models.Recipe
import kotlin.math.roundToInt

class RecipeViewHolder (
    override val containerView: View,
    detailsAction: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { detailsAction(adapterPosition) }
    }

    fun bind(recipe: Recipe) {
        Picasso
            .get()
            .load(recipe.imageUrl)
            .into(recipeImage)
        recipeTitle.text = recipe.title
        recipePublisher.text = recipe.publisher
        recipeSocialRank.text = recipe.socialRank.toDouble().roundToInt().toString()

    }

}