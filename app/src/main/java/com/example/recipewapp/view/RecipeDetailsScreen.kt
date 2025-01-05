package com.example.recipewapp.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.example.recipewapp.view.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(recipeId: Int, viewModel: RecipeDetailsViewModel = hiltViewModel()) {
    val recipeDetails by viewModel.recipeDetails.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipeDetails(recipeId)
    }

    recipeDetails?.let { details ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                Text(
                    text = details.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Ready in ${details.readyInMinutes} mins",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Servings: ${details.servings}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }


            item {
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                details.extendedIngredients.forEach { ingredient ->
                    Text(
                        text = "- ${ingredient.amount} ${ingredient.unit} ${ingredient.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


            item {
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = details.instructions ?: "No instructions available.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    } ?: run {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


