package com.sammy.paparadoorbell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sammy.paparadoorbell.feature.favoriterecipe.FavoriteRecipeScreen
import com.sammy.paparadoorbell.feature.home.HomeScreen
import com.sammy.paparadoorbell.feature.recipe.RecipeScreen
import com.sammy.paparadoorbell.feature.recipedetail.RecipeDetailScreen
import com.sammy.paparadoorbell.feature.recipedetail.RecipeDetailViewModel
import com.sammy.paparadoorbell.feature.splash.SplashScreen

@Composable
fun SpoonacularNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = SpoonacularDestination.SPLASH,
    navActions: SpoonacularNavigationActions = remember(navController) {
        SpoonacularNavigationActions(navController)
    }
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = SpoonacularDestination.SPLASH
        ) {
            SplashScreen(
                onSplashFinished = {
                    navActions.navigateToHome()
                }
            )
        }


        composable(
            route = SpoonacularDestination.RECIPES
        ) {
            RecipeScreen(navActions) {
                navActions.navigateToRecipeDetail(it)
            }
        }

        composable(
            route = SpoonacularDestination.HOME
        ) {
            HomeScreen {
                navActions.navigateToRecipe()
            }
        }

        composable(
            route = SpoonacularDestination.FAVORITE_RECIPE
        ) {
            FavoriteRecipeScreen(navActions) {
                navActions.navigateToRecipe()
            }
        }

        composable("recipeDetail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            if (recipeId != null) {
                val viewModel: RecipeDetailViewModel = hiltViewModel()
                RecipeDetailScreen(
                    recipeId = recipeId,
                    navBack = { navActions.navigateToRecipe() },
                    onFavoriteClick = { viewModel.markAsFavorite(recipeId) },
                    onUnFavoriteClick = { viewModel.markAsUnFavorite(recipeId) }
                )
            }
        }

    }
}
