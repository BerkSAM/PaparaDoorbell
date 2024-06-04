package com.sammy.paparadoorbell.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes

@Database(entities = [LocalRecipes::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}