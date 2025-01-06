package com.example.recipewapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.recipewapp.model.Recipe

@Composable
fun RecipeSearchScreen(navController: NavController, viewModel: RecipeSearchViewModel) {
    val recipes by viewModel.recipes.collectAsState()
    val maxIngredients = 5
    var ingredientsList by remember { mutableStateOf(listOf("", "")) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Search Recipes",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0, 13, 45, 255)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ingredientsList.forEachIndexed { index, ingredient ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = ingredient,
                            onValueChange = { newValue ->
                                ingredientsList = ingredientsList.toMutableList().apply {
                                    this[index] = newValue
                                }
                            },
                            label = { Text("Ingredient ${index + 1}") },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if (ingredientsList.size > 1) {
                            Button(
                                onClick = {
                                    ingredientsList = ingredientsList.toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                            ) {
                                Text("-")
                            }
                        }
                    }
                }

                if (ingredientsList.size < maxIngredients) {
                    Button(
                        onClick = {
                            ingredientsList = ingredientsList.toMutableList().apply {
                                add("")
                            }
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text("+ Add Ingredient")
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recipes) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onToggleFavorite = { viewModel.toggleFavoriteStatus(it) },
                        navController = navController
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        val ingredientsString =
                            ingredientsList.filter { it.isNotBlank() }.joinToString(",")
                        viewModel.fetchRecipes(ingredientsString)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Search Recipes")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { navController.navigate("favourites") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("View Favourites")
                }
            }
        }
    }
}


@Composable
fun RecipeItem(recipe: Recipe, onToggleFavorite: (Recipe) -> Unit, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("recipeDetails/${recipe.id}") },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { onToggleFavorite(recipe) }) {
                    Icon(
                        imageVector = if (recipe.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = if (recipe.isFavorite) "Remove from Favorites" else "Add to Favorites",
                        tint = if (recipe.isFavorite) Color.Yellow else Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
