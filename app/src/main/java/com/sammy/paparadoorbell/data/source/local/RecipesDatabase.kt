package com.sammy.paparadoorbell.data.source.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipesDetail

@Database(
    entities = [LocalRecipes::class, LocalRecipesDetail::class],
    version = 4,
    exportSchema = false,
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}