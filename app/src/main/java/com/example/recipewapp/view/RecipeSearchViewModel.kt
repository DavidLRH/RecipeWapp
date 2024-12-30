package com.example.recipewapp.view

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


    fun fetchRecipes(ingredient: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _recipes.value = recipeController.getRecipes(ingredient)
            } catch (e: Exception) {
                println("error fetching recipes: " + e.message)
            }
        }
    }

    init {
        println("viewmodel started")
    }
}