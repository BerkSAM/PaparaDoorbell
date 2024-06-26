package com.sammy.paparadoorbell.data

import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import com.sammy.paparadoorbell.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface SpoonacularRepository {
    suspend fun getRecipes(type:String): Flow<ApiResult<RecipesResponse>>
    suspend fun getRecipesDetail(recipeId: Int): Flow<ApiResult<RecipeDetailResponse>>


}