package com.sammy.paparadoorbell.feature.recipedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sammy.paparadoorbell.SpoonacularNavigationActions
import com.sammy.paparadoorbell.feature.recipedetail.components.IngredientList
import com.sammy.paparadoorbell.feature.recipedetail.components.InstructionsCard
import com.sammy.paparadoorbell.feature.recipedetail.components.RecipeImageHeader
import com.sammy.paparadoorbell.ui.componentes.BottomBar
import com.sammy.paparadoorbell.ui.theme.mediumfont
import com.sammy.paparadoorbell.ui.theme.regularfont
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    navActions: SpoonacularNavigationActions,
    recipeId: Int,
    navBack: () -> Unit,
    onFavoriteClick: () -> Unit,
    onUnFavoriteClick: () -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetails(recipeId)
    }
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(Color.Transparent),
                title = {
                    Text(
                        text = state.value.recipe?.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (state.value.isFav) {
                            onUnFavoriteClick()
                            viewModel._uiState.value = state.value.copy(isFav = false)
                        } else {
                            onFavoriteClick()
                            viewModel._uiState.value = state.value.copy(isFav = true)
                        }
                    }) {
                        Icon(
                            imageVector = if (state.value.isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (state.value.isFav) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = { BottomBar(navActions) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = scrollState,
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                RecipeImageHeader(state.value.recipe?.image)
            }
            item {
                Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)) {
                    Text(
                        text = state.value.recipe?.title ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Time",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${state.value.recipe?.readyInMinutes} Minutes",
                                fontSize = 12.sp,
                                fontFamily = regularfont,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Servings",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${state.value.recipe?.servings} Serving",
                                fontSize = 12.sp,
                                fontFamily = regularfont,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

            item {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "Ingredients",
                        fontFamily = mediumfont,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    IngredientList(state.value.recipe?.extendedIngredients)
                }
            }

            item {
                Text(
                    text = "Instructions",
                    fontFamily = mediumfont,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp
                    )
                )
            }
            item {
                state.value.recipe?.instructions?.let {
                    InstructionsCard(instructions = it)
                }
            }
        }

    }
}
