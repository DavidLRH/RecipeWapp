package com.example.recipewapp.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.recipewapp.model.RecipeDetailsResponse

object ApiClient {
    private const val BASE_URL = "https://api.spoonacular.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

interface ApiService {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("includeIngredients") ingredients: String,
        @Query("apiKey") apiKey: String
    ): RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @retrofit2.http.Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String
    ): RecipeDetailsResponse
}

data class RecipeResponse(val results: List<ApiRecipe>)

data class ApiRecipe(val id: Int, val title: String, val image: String)