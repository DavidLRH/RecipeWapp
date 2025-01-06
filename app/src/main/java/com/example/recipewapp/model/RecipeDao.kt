package com.example.recipewapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes():  List<Recipe>


    @Query("UPDATE recipes SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    @Query("SELECT * FROM recipes WHERE is_favorite = 1")
    suspend fun getFavoriteRecipes(): List<Recipe>

}