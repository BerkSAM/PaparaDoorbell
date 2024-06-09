package com.sammy.paparadoorbell.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipeDetail")
data class LocalRecipesDetail(
    @PrimaryKey val id: Int?,
    val aggregateLikes: Int?,
    val cheap: Boolean?,
    val creditsText: String?,
    val dairyFree: Boolean?,
    val gaps: String?,
    val glutenFree: Boolean?,
    val healthScore: Int?,
    val image: String?,
    val imageType: String?,
    val instructions: String?,
    val lowFodmap: Boolean?,
    val pricePerServing: Double?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceName: String?,
    val sourceUrl: String?,
    val spoonacularScore: Double?,
    val spoonacularSourceUrl: String?,
    val summary: String?,
    val sustainable: Boolean?,
    val title: String?,
    val vegan: Boolean?,
    val vegetarian: Boolean?,
    val veryHealthy: Boolean?,
    val veryPopular: Boolean?,
    val weightWatcherSmartPoints: Int?,
    val isFav: Boolean = false
)