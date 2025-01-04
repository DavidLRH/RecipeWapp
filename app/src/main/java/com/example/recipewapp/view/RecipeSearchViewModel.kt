package com.example.recipewapp.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.recipewapp.model.RecipeDetailsResponse
import com.example.recipewapp.repository.RecipeRepository


@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val recipeController: RecipeController,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private var _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _favorites = MutableStateFlow<List<Recipe>>(emptyList())
    val favorites: StateFlow<List<Recipe>> = _favorites

    private val _recipeDetails = MutableLiveData<RecipeDetailsResponse?>()
    val recipeDetails: LiveData<RecipeDetailsResponse?> = _recipeDetails

    fun fetchRecipes(ingredient: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedRecipes = recipeController.getRecipes(ingredient)
                Log.d("RecipeSearchViewModel", "Fetched Recipes: $fetchedRecipes")
                _recipes.value = fetchedRecipes
            } catch (e: Exception) {
                Log.e("RecipeSearchViewModel", "Error fetching recipes", e)
            }
        }
    }

    fun toggleFavoriteStatus(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recipeController.updateFavoriteStatus(recipe.id, !recipe.isFavorite)

                _recipes.value = _recipes.value.map {
                    if (it.id == recipe.id) it.copy(isFavorite = !recipe.isFavorite) else it
                }

                _favorites.value = recipeController.getFavoriteRecipes()
            } catch (e: Exception) {
                Log.e("RecipeSearchViewModel", "Error toggling favorite status", e)
            }
        }
    }

    fun fetchRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                val details = recipeRepository.fetchRecipeDetails(recipeId)
                _recipeDetails.postValue(details)
                Log.d("RecipeDetailsViewModel", "Fetched Recipe Details: $details")
            } catch (e: Exception) {
                Log.e("RecipeDetailsViewModel", "Error fetching recipe details", e)
            }
        }
    }

    fun fetchFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _favorites.value = recipeController.getFavoriteRecipes()
            } catch (e: Exception) {
                Log.e("RecipeSearchViewModel", "Error fetching favorites", e)
            }
        }
    }

    init {
        Log.d("RecipeSearchViewModel", "ViewModel initialized")
    }
}
