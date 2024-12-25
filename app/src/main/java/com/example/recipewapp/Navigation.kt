package com.example.recipewapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipewapp.view.RecipeSearchScreen
import com.example.recipewapp.view.RecipeSearchViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecipeWApp(viewModel: RecipeSearchViewModel = hiltViewModel()){
    val navController = rememberNavController()

    // Set up the navigation host
    NavHost(navController = navController, startDestination = Screen.RecipeSearchScreen.route) {
        composable(Screen.RecipeSearchScreen.route) {
            RecipeSearchScreen(navController, viewModel)
        }
    }
}

sealed class Screen(val route: String) {
    object RecipeSearchScreen : Screen("search")
}