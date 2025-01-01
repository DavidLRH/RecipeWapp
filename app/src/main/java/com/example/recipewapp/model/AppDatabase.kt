package com.example.recipewapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Recipe::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE recipes ADD COLUMN is_favorite INTEGER NOT NULL DEFAULT 0")
            }
        }


        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE recipes RENAME TO old_recipes")

                database.execSQL(
                    """
                    CREATE TABLE recipes (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        title TEXT NOT NULL DEFAULT 'undefined',
                        image TEXT NOT NULL DEFAULT 'undefined',
                        new_column_name TEXT NOT NULL DEFAULT '',
                        is_favorite INTEGER NOT NULL DEFAULT 0
                    )
                    """
                )


                database.execSQL(
                    """
                    INSERT INTO recipes (id, title, image)
                    SELECT idMeal, name, thumbnail FROM old_recipes
                    """
                )


                database.execSQL("DROP TABLE old_recipes")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
