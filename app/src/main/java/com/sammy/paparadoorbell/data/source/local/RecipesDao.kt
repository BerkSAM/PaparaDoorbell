package com.sammy.paparadoorbell.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipesDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    // insert many
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipes(recipes: List<LocalRecipes>)

    // insert one
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeDetail(recipeDetail: LocalRecipesDetail)

    // observe all
    @Query("SELECT * FROM recipes")
    fun observeRecipes(): Flow<List<LocalRecipes>>

    // get local data
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<LocalRecipes>

    // get local fav data
    @Query("SELECT * FROM recipeDetail WHERE isFav = 1")
    fun getRecipeFav(): Flow<List<LocalRecipesDetail>>

    // get local recipe detail data
    @Query("SELECT * FROM recipeDetail WHERE id = :id")
    fun getRecipeDetailById(id: Int): Flow<LocalRecipesDetail>

    @Query("UPDATE recipeDetail SET isFav = 1 WHERE id = :recipeId")
    suspend fun markAsFavorite(recipeId: Int)

    @Query("UPDATE recipeDetail SET isFav = 1 WHERE id = :recipeId")
    suspend fun markAsFavoriteRecipe(recipeId: Int)
}