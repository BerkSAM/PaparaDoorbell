package com.sammy.paparadoorbell.ui.componentes

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sammy.paparadoorbell.SpoonacularDestination
import com.sammy.paparadoorbell.SpoonacularNavigationActions
import com.sammy.paparadoorbell.ui.theme.CustomColors

@Composable
fun BottomBar(navActions: SpoonacularNavigationActions) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    BottomNavigation(
        backgroundColor = CustomColors.PRIMARY_COLOR,
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            },
            selected = currentRoute == SpoonacularDestination.RECIPES,
            onClick = { navActions.navigateToRecipe() }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites",
                    tint = Color.White
                )
            },
            selected = currentRoute == SpoonacularDestination.FAVORITE_RECIPE,
            onClick = { navActions.navigateToFavoriteRecipe() }
        )
    }
}