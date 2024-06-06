package com.sammy.paparadoorbell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes
import com.sammy.paparadoorbell.feature.home.HomeScreen
import com.sammy.paparadoorbell.feature.recipe.RecipeScreen
import com.sammy.paparadoorbell.feature.recipedetail.RecipeDetailScreen
import com.sammy.paparadoorbell.feature.splash.SplashScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun SpoonacularNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = SpoonacularDestination.SPLASH,
    navActions: SpoonacularNavigationActions = remember(navController) {
        SpoonacularNavigationActions(navController)
    }
) {

    val currentNavigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavigationBackStackEntry?.destination?.route ?: startDestination

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
            RecipeScreen {
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

        composable("recipeDetail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            if (recipeId != null) {
                RecipeDetailScreen(recipeId = recipeId,navBack = { navActions.navigateToRecipe() })
            }
        }


    }
}
