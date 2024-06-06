package com.sammy.paparadoorbell.data.source.network

import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpoonacularApiService {
    @GET("/recipes/complexSearch?apiKey=a4eb230a10e24df7920ebec5c21fe006&offset=4&number=10")
    suspend fun getRecipes(): Response<RecipesResponse>

    @GET("/recipes/{recipeId}/information?apiKey=a4eb230a10e24df7920ebec5c21fe006")
    suspend fun getRecipesDetail(@Path("recipeId") recipeId: Int): Response<RecipeDetailResponse>


}