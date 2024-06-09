package com.sammy.paparadoorbell.data.source.network

import com.sammy.paparadoorbell.BuildConfig.API_KEY
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import com.sammy.paparadoorbell.utils.ApiResult
import com.sammy.paparadoorbell.utils.apiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpoonacularNetworkDataSource @Inject constructor(
    private val spoonacularApi: SpoonacularApiService
) : NetworkDataSource {
    override suspend fun getRecipes(type: String): Flow<ApiResult<RecipesResponse>> = apiFlow {
        spoonacularApi.getRecipes(API_KEY,type)
    }

    override suspend fun getRecipesDetail(recipeId: Int): Flow<ApiResult<RecipeDetailResponse>> = apiFlow {
        spoonacularApi.getRecipesDetail(recipeId)
    }

}