package com.example.recipewapp.repository


import com.example.recipewapp.model.Recipe
import com.example.recipewapp.model.AppDatabase
import com.example.recipewapp.utils.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(private val db: AppDatabase) {

    private val apiService = ApiClient.apiService

    suspend fun fetchRecipes(ingredient1: String, ingredient2: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getRecipes("$ingredient1,$ingredient2", "4b4fe95b701148609f5729071dc5b5c0")

            val recipes = response.results.map {
                Recipe(it.id.toLong(), it.title, it.image)
            }
            db.recipeDao().insertRecipes(recipes)
            recipes
        }
    }

    suspend fun getCachedRecipes(): List<Recipe> {
        return db.recipeDao().getAllRecipes()
    }
}