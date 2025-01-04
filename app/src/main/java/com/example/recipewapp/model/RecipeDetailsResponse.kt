package com.example.recipewapp.model

data class RecipeDetailsResponse(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val image: String,
    val instructions: String?,
    val extendedIngredients: List<IngredientDetails>
)

data class IngredientDetails(
    val name: String,
    val amount: Double,
    val unit: String
)
