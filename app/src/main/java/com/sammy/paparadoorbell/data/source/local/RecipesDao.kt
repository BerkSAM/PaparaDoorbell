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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<LocalRecipes>)

    // insert one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeDetail(recipeDetail: LocalRecipesDetail)

    // observe all
    @Query("SELECT * FROM recipes")
    fun observeRecipes(): Flow<List<LocalRecipes>>

    // get local data
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<LocalRecipes>

    // get local recipe detail data
    @Query("SELECT * FROM recipeDetail WHERE id = :id")
    fun getRecipeDetailById(id: Int): Flow<LocalRecipesDetail>
}