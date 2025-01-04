package com.example.recipewapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipewapp.model.RecipeDetailsResponse
import com.example.recipewapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipeDetails = MutableLiveData<RecipeDetailsResponse?>()
    val recipeDetails: LiveData<RecipeDetailsResponse?> = _recipeDetails

    fun fetchRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                val details = repository.fetchRecipeDetails(recipeId)
                _recipeDetails.postValue(details)
            } catch (e: Exception) {
                _recipeDetails.postValue(null)
            }
        }
    }
}
