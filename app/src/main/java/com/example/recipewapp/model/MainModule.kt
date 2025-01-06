package com.example.recipewapp.model

import android.app.Application
import androidx.room.Room
import com.example.recipewapp.repository.RecipeController
import com.example.recipewapp.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideRecipeController(recipeRepository: RecipeRepository): RecipeController {
        return RecipeController(recipeRepository)
    }

    @Provides
    fun provideRecipeRepository(dao: RecipeDao): RecipeRepository {
        return RecipeRepository(dao)
    }

    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao {
        return db.recipeDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "recipe_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}