package com.example.recipewapp.view

import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipewapp.R
import com.example.recipewapp.repository.RecipeController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var controller: RecipeController
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller = RecipeController(applicationContext)

        val ingredient1Field = findViewById<EditText>(R.id.ingredient1)
        val ingredient2Field = findViewById<EditText>(R.id.ingredient2)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(listOf())
        recyclerView.adapter = recipeAdapter

        searchButton.setOnClickListener {
            val ingredient1 = ingredient1Field.text.toString()
            val ingredient2 = ingredient2Field.text.toString()
            if (ingredient1.isNotBlank() && ingredient2.isNotBlank()) {
                fetchRecipes(ingredient1, ingredient2)
            } else {
                Toast.makeText(this, "Please enter two ingredients", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchRecipes(ingredient1: String, ingredient2: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val recipes = controller.getRecipes(ingredient1, ingredient2)
            recipeAdapter.updateRecipes(recipes)
        }
    }
}