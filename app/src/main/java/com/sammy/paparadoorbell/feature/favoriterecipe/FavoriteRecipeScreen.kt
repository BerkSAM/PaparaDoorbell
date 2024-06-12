package com.sammy.paparadoorbell.feature.favoriterecipe

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sammy.paparadoorbell.SpoonacularNavigationActions
import com.sammy.paparadoorbell.feature.favoriterecipe.components.CustomRecipeCard
import com.sammy.paparadoorbell.ui.componentes.BottomBar
import com.sammy.paparadoorbell.utils.stripHtml

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteRecipeScreen(
    navActions: SpoonacularNavigationActions,
    viewModel: FavoriteRecipeViewModel = hiltViewModel(),
    navFavRecipe: () -> Unit
) {
    val favoriteRecipes by viewModel.favoriteRecipes.observeAsState()

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Favorite Recipes") },
                navigationIcon = {
                    IconButton(onClick = { navFavRecipe() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomBar(navActions) },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp, bottom = 56.dp)
            ) {
                favoriteRecipes?.let { recipes ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(recipes.size) { index ->
                            Box {
                                CustomRecipeCard(
                                    id = recipes[index].id ?: 0,
                                    image = recipes[index].image,
                                    name = recipes[index].title,
                                    instruction = recipes[index].instructions?.stripHtml(),
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}



