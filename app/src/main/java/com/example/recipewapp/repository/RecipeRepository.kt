package com.example.recipewapp.repository


import com.example.recipewapp.model.Recipe
import com.example.recipewapp.model.AppDatabase
import com.example.recipewapp.model.RecipeDao
import com.example.recipewapp.utils.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDao: RecipeDao) {

    private val apiService = ApiClient.apiService

    suspend fun fetchRecipes(ingredient: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getRecipes("$ingredient,", "4b4fe95b701148609f5729071dc5b5c0")

            val recipes = response.results.map {
                Recipe(it.id.toLong(), it.title, it.image)
            }
            recipeDao.insertRecipes(recipes)
            recipes
        }
    }

    suspend fun getCachedRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes()
    }
}