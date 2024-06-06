package com.sammy.paparadoorbell.data.source.network

import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpoonacularApiService {
    @GET("/recipes/complexSearch?apiKey=8d2d3c918120482ea0cdde614e6bd2df&offset=4&number=4")
    suspend fun getRecipes(): Response<RecipesResponse>

    @GET("/recipes/{recipeId}/information?apiKey=8d2d3c918120482ea0cdde614e6bd2df")
    suspend fun getRecipesDetail(@Path("recipeId") recipeId: Int): Response<RecipeDetailResponse>


}