package com.example.recipewapp.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.recipewapp.RecipeWApp
import com.example.recipewapp.ui.theme.RecipeWappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipeSearchViewModel::class.java)

        enableEdgeToEdge()
        setContent {
            RecipeWappTheme {
                RecipeWApp(viewModel)
            }
        }
    }
}