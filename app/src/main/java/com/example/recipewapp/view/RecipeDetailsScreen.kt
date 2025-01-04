package com.example.recipewapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.recipewapp.view.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(recipeId: Int, viewModel: RecipeDetailsViewModel = hiltViewModel()) {
    val recipeDetails by viewModel.recipeDetails.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipeDetails(recipeId)
    }

    recipeDetails?.let { details ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = details.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Ready in ${details.readyInMinutes} minutes")
            Text(text = "Servings: ${details.servings}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ingredients:")
            details.extendedIngredients.forEach { ingredient ->
                Text(text = "- ${ingredient.amount} ${ingredient.unit} ${ingredient.name}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Instructions:")
            Text(text = details.instructions ?: "No instructions available.")
        }
    } ?: run {
        Text("Loading...")
    }
}
