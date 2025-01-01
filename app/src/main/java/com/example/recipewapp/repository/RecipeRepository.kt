package com.example.recipewapp.repository


import android.util.Log
import com.example.recipewapp.model.Recipe
import com.example.recipewapp.model.RecipeDao
import com.example.recipewapp.utils.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDao: RecipeDao) {

    private val apiService = ApiClient.apiService

    suspend fun fetchRecipes(ingredients: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getRecipes("$ingredients,", "4b4fe95b701148609f5729071dc5b5c0")


            val recipes = response.results.map {
                Recipe(it.id.toLong(), it.title, it.image)
            }
            recipeDao.insertRecipes(recipes)
            Log.d("RecipeRepository", "API Response: $response")
            recipes
        }
    }

    suspend fun getCachedRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes()
    }

    suspend fun updateFavoriteStatus(recipeId: Long, isFavorite: Boolean) {
        recipeDao.updateFavoriteStatus(recipeId, isFavorite)
    }

    // New method: Get all favorite recipes
    suspend fun getFavoriteRecipes(): List<Recipe> {
        return recipeDao.getFavoriteRecipes()
    }


}