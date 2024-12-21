package com.example.recipewapp.repository

import android.content.Context
import com.example.recipewapp.model.AppDatabase
import com.example.recipewapp.model.Recipe

class RecipeController(context: Context) {
    private val repository = RecipeRepository(AppDatabase.getDatabase(context))

    suspend fun getRecipes(ingredient1: String, ingredient2: String): List<Recipe> {
        return repository.fetchRecipes(ingredient1, ingredient2)
    }

    suspend fun getCachedRecipes(): List<Recipe> {
        return repository.getCachedRecipes()
    }
}