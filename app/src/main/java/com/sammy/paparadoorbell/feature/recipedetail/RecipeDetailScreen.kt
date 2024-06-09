package com.sammy.paparadoorbell.feature.recipedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.sammy.paparadoorbell.ui.theme.mediumfont
import com.sammy.paparadoorbell.ui.theme.regularfont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
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
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
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
        }
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
                    Text(
                        text = "${state.value.recipe?.readyInMinutes} Minutes - ${state.value.recipe?.servings} Serving",
                        fontSize = 12.sp,
                        fontFamily = regularfont,
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)
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

@Composable
fun InstructionsCard(instructions: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Text(
            text = instructions,
            fontSize = 12.sp,
            fontFamily = regularfont,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun RecipeImageHeader(imageUrl: String?) {
    Card(
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun IngredientList(ingredients: List<com.sammy.paparadoorbell.data.source.network.response.recipesDetail.ExtendedIngredient?>?) {
    Column(Modifier.padding(8.dp)) {
        ingredients?.chunked(3)?.forEach { rowIngredients -> // Adjust the chunk size based on how many items you want per row
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between items
            ) {
                rowIngredients.forEach { ingredient ->
                    if (ingredient != null) {
                        Box(
                            modifier = Modifier
                                .size(110.dp, 150.dp) // Set the fixed size with height of 130.dp
                                .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "- ${ingredient.original}",
                                fontFamily = regularfont,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // Add vertical spacing between rows
        }
    }
}
