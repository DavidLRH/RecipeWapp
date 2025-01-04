package com.example.recipewapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipewapp.view.RecipeSearchScreen
import com.example.recipewapp.view.RecipeSearchViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipewapp.view.FavouriteRecipesScreen
import com.example.recipewapp.view.RecipeDetailsScreen


@Composable
fun RecipeWApp(viewModel: RecipeSearchViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.RecipeSearchScreen.route) {
        composable(Screen.RecipeSearchScreen.route) {
            RecipeSearchScreen(navController, viewModel)
        }
        composable(Screen.FavouriteRecipesScreen.route) {
            FavouriteRecipesScreen(navController, viewModel)
        }
        composable("recipeDetails/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toInt() ?: 0
            RecipeDetailsScreen(recipeId = recipeId)
        }

    }
}

sealed class Screen(val route: String) {
    object RecipeSearchScreen : Screen("search")
    object FavouriteRecipesScreen : Screen("favourites")
}