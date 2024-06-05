package com.sammy.paparadoorbell

import android.util.Log
import androidx.navigation.NavHostController

object SpoonacularDestination {
    const val SPLASH = "splash"
    const val RECIPES = "recipes"
    const val RECIPE_DETAIL = "recipeDetail/{id}"
}

class SpoonacularNavigationActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(SpoonacularDestination.RECIPES) {
            popUpTo(SpoonacularDestination.SPLASH) {
                inclusive = true
                saveState = true
            }
        }
    }

    fun navigateToRecipeDetail(id: Int) {
        Log.v("SpoonacularNavigationActions", "navigateToCDetail: $id")
        navController.navigate(
            SpoonacularDestination.RECIPE_DETAIL.replace("{id}", id.toString()),
        ) {
            popUpTo(SpoonacularDestination.RECIPES) {
                saveState = true
            }
        }
    }
}