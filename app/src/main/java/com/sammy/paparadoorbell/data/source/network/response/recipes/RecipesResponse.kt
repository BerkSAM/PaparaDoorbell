package com.sammy.paparadoorbell.data.source.network.response.recipes

data class RecipesResponse(
    val results: List<Recipe>
)

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val weightWatcherSmartPoints: Int
)