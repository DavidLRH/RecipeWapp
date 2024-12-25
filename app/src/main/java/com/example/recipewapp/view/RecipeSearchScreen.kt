package com.example.recipewapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.recipewapp.model.Recipe

@Composable
fun RecipeSearchScreen(navController: NavController, viewModel: RecipeSearchViewModel) {
    val recipes by viewModel.recipes.collectAsState()
    var ingredients by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            TextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("chicken, pasta,...") }
            )

            Row {
                Checkbox(
                    checked = true,
                    onCheckedChange = {checked = it}
                )
                Text("ignore common pantry items")
            }

            Button(onClick = { viewModel.fetchRecipes(ingredients) }) {
                Text("Search recipes")
            }



            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recipes) { recipe ->
                    RecipeItem(recipe)
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
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