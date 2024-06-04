package com.sammy.paparadoorbell.data

import android.util.Log
import com.sammy.paparadoorbell.data.source.local.RecipesDao
import com.sammy.paparadoorbell.data.source.network.NetworkDataSource
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.toLocal
import com.sammy.paparadoorbell.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpoonacularRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: RecipesDao
) : SpoonacularRepository {

    override suspend fun getRecipes(): Flow<ApiResult<RecipesResponse>> {
        val recipesResponse = networkDataSource.getRecipes()
        recipesResponse.collect { value ->
            when (value) {
                is ApiResult.Success -> {
                    value.data?.results?.forEach { recipe ->
                        Log.d("SpoonacularRepository", "Recipe: ${recipe.title}")
                    }
                    localDataSource.insertRecipes(value.data?.toLocal().orEmpty())

                }
                is ApiResult.Error -> {
                    Log.e("SpoonacularRepository", "Error: ${value.message}")
                }
                else -> {
                    Log.d("SpoonacularRepository", "Recipe: ${value}")
                }
            }
        }
        return recipesResponse
    }


}