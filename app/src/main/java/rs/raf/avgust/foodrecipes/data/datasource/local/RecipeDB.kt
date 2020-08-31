package rs.raf.avgust.foodrecipes.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.avgust.foodrecipes.data.datasource.local.converters.DateConverter
import rs.raf.avgust.foodrecipes.data.datasource.local.converters.StringListConverter
import rs.raf.avgust.foodrecipes.data.models.IngredientsEntity
import rs.raf.avgust.foodrecipes.data.models.RecipeEntity
import rs.raf.avgust.foodrecipes.data.models.SavedRecipeEntity

@Database(
    entities = [RecipeEntity::class, IngredientsEntity::class, SavedRecipeEntity::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(StringListConverter::class, DateConverter::class)
abstract class RecipeDB : RoomDatabase(){
    abstract fun getRecipeDao(): RecipeDao
}