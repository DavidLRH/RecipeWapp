package com.example.recipewapp.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FavouriteRecipesScreen(navController: NavController, viewModel: RecipeSearchViewModel) {

    val favorites = viewModel.favorites.collectAsState().value


    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {

            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Favourite Recipes",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0, 13, 45, 255)
                )
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.Magenta)
                    .padding(4.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { recipe ->
                    RecipeItem(recipe = recipe, onToggleFavorite = { viewModel.toggleFavoriteStatus(it) })
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate(route = "search") },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text("Search Recipes")
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}