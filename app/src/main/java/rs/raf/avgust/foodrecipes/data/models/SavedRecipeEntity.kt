package rs.raf.avgust.foodrecipes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "savedRecipes")
data class SavedRecipeEntity(
    @PrimaryKey(autoGenerate = true) val room_id: Int,
    val id: String,
    val title: String,
    val publisher: String,
    val recipe_id: String,
    val category: String,
    val date: Date
)