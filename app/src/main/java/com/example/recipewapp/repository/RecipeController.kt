package com.example.recipewapp.repository

import android.content.Context
import com.example.recipewapp.model.AppDatabase
import com.example.recipewapp.model.Recipe
import javax.inject.Inject

class RecipeController @Inject constructor(
    private val repository: RecipeRepository) {

    suspend fun getRecipes(ingredient: String): List<Recipe> {
        return repository.fetchRecipes(ingredient)
    }

    suspend fun getCachedRecipes(): List<Recipe> {
        return repository.getCachedRecipes()
    }

    suspend fun updateFavoriteStatus(recipeId: Long, isFavorite: Boolean) {
        repository.updateFavoriteStatus(recipeId, isFavorite)
    }

    suspend fun getFavoriteRecipes(): List<Recipe> {
        return repository.getFavoriteRecipes()
    }
}