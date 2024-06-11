package com.sammy.paparadoorbell.data.source.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipesDetail
import com.sammy.paparadoorbell.data.source.local.entity.Notification

@Database(
    entities = [LocalRecipes::class, LocalRecipesDetail::class, Notification::class],
    version = 6,
    exportSchema = false,
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}