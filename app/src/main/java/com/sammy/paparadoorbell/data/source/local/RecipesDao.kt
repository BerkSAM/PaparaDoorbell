package com.sammy.paparadoorbell.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipesDetail
import com.sammy.paparadoorbell.data.source.local.entity.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    // insert bulk recipes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipes(recipes: List<LocalRecipes>)

    // insert one recipe to detail
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeDetail(recipeDetail: LocalRecipesDetail)

    // observe all recipes
    @Query("SELECT * FROM recipes")
    fun observeRecipes(): Flow<List<LocalRecipes>>

    // get local recipes data
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<LocalRecipes>

    // get local fav recipes data
    @Query("SELECT * FROM recipeDetail WHERE isFav = 1")
    fun getRecipeFav(): Flow<List<LocalRecipesDetail>>

    // get local recipes data
    @Query("SELECT isFav FROM recipeDetail WHERE id = :id")
    fun getRecipeDetailById(id: Int): Flow<LocalRecipesDetail>

    // mark as favorite
    @Query("UPDATE recipeDetail SET isFav = 1 WHERE id = :recipeId")
    suspend fun markAsFavorite(recipeId: Int)

    // mark as unfavorite
    @Query("UPDATE recipeDetail SET isFav = 0 WHERE id = :recipeId")
    suspend fun markAsUnFavorite(recipeId: Int)

    // get all notifications data
    @Query("SELECT * FROM notifications ORDER BY id DESC LIMIT 10")
    fun getNotifications(): Flow<List<Notification>>

    // Insert notification table data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotification(notifications: Notification)

    // Delete all notifications table data
    @Query("DELETE FROM notifications")
    suspend fun deleteAll()

    // Delete all recipes table data
    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()
}