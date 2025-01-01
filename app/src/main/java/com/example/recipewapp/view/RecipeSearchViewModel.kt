package com.example.recipewapp.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.foreignKeyCheck
import com.example.recipewapp.model.Recipe
import com.example.recipewapp.model.RecipeDao
import com.example.recipewapp.repository.RecipeController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val recipeController: RecipeController,
) : ViewModel() {
    private var _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    var recipes: StateFlow<List<Recipe>> = _recipes


    private val _favorites = MutableStateFlow<List<Recipe>>(emptyList())
    val favorites: StateFlow<List<Recipe>> = _favorites


    fun fetchRecipes(ingredient: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedRecipes = recipeController.getRecipes(ingredient)
                Log.d("RecipeSearchViewModel", "Fetched Recipes: $fetchedRecipes") // Log recipes
                _recipes.value = fetchedRecipes
            } catch (e: Exception) {
                Log.e("RecipeSearchViewModel", "Error fetching recipes", e)
            }
        }
    }

    fun toggleFavoriteStatus(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeController.updateFavoriteStatus(recipe.id, !recipe.isFavorite)


            val updatedRecipes = recipeController.getRecipes("")
            _recipes.value = updatedRecipes
            _favorites.value = recipeController.getFavoriteRecipes()
        }
    }

    fun fetchFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            _recipes.value = recipeController.getFavoriteRecipes()
        }
    }


    init {
        println("viewmodel started")
    }
}