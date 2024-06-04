package com.sammy.paparadoorbell.data.source.network

import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {

    suspend fun getRecipes(): Flow<ApiResult<RecipesResponse>>
}
