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

    @GET("/recipes/{recipeId}/information?apiKey=a25690519422419eabc6cc3a67b2b7e0")
    suspend fun getRecipesDetail(@Path("recipeId") recipeId: Int): Response<RecipeDetailResponse>

    companion object {
        const val API_KEY = "a25690519422419eabc6cc3a67b2b7e0"
    }
}