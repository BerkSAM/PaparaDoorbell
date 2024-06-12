package com.sammy.paparadoorbell


import androidx.navigation.NavHostController

object SpoonacularDestination {
    const val HOME = "home"
    const val SPLASH = "splash"
    const val RECIPES = "recipes"
    const val RECIPE_DETAIL = "recipeDetail/{id}"
    const val FAVORITE_RECIPE = "favoriterecipe"
}

class SpoonacularNavigationActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(SpoonacularDestination.HOME) {
            popUpTo(SpoonacularDestination.SPLASH) {
                inclusive = true
                saveState = true
            }
        }
    }

    fun navigateToRecipe() {
        navController.navigate(SpoonacularDestination.RECIPES) {
            popUpTo(SpoonacularDestination.HOME) {
                inclusive = true
                saveState = true
            }
        }
    }

    fun navigateToFavoriteRecipe() {
        navController.navigate(SpoonacularDestination.FAVORITE_RECIPE) {
            popUpTo(SpoonacularDestination.RECIPES) {
                inclusive = true
                saveState = true
            }
        }
    }

    fun navigateToRecipeDetail(id: Int) {
        navController.navigate(
            SpoonacularDestination.RECIPE_DETAIL.replace("{id}", id.toString()),
        ) {
            popUpTo(SpoonacularDestination.RECIPES) {
                saveState = true
            }
        }
    }
}