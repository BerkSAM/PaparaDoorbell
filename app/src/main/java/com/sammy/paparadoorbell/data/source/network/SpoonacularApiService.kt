package com.sammy.paparadoorbell.data.source.network

import com.sammy.paparadoorbell.BuildConfig.API_KEY
import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SpoonacularApiService {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("type") type: String
    ): Response<RecipesResponse>

    @GET("/recipes/{recipeId}/information")
    suspend fun getRecipesDetail(
        @Path("recipeId") recipeId: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<RecipeDetailResponse>


}