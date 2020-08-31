package rs.raf.avgust.foodrecipes.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.avgust.foodrecipes.data.datasource.local.RecipeDB
import rs.raf.avgust.foodrecipes.data.datasource.remote.RecipeService
import rs.raf.avgust.foodrecipes.data.repositories.RecipeRepository
import rs.raf.avgust.foodrecipes.data.repositories.RecipeRepositoryImpl
import rs.raf.avgust.foodrecipes.presentation.viewmodel.RecipeViewModel

val recipeModule = module {

    viewModel { RecipeViewModel(recipeRepository = get()) }

    single<RecipeRepository> { RecipeRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<RecipeDB>().getRecipeDao() }

    single<RecipeService> { create(retrofit = get()) }

}