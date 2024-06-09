package com.sammy.paparadoorbell.data.source.network

import com.sammy.paparadoorbell.data.source.network.response.recipes.RecipesResponse
import com.sammy.paparadoorbell.data.source.network.response.recipesDetail.RecipeDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SpoonacularApiService {

    @GET("/recipes/complexSearch?offset=4&number=10")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("type") type: String
    ): Response<RecipesResponse>

    @GET("/recipes/{recipeId}/information?apiKey=a4eb230a10e24df7920ebec5c21fe006")
    suspend fun getRecipesDetail(@Path("recipeId") recipeId: Int): Response<RecipeDetailResponse>

    companion object {
        const val API_KEY = "a4eb230a10e24df7920ebec5c21fe006"
    }
}