package com.sammy.paparadoorbell.feature.recipedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    navBack: () -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
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
                    IconButton(onClick = { /* Handle favorite action */ }) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite", tint = MaterialTheme.colorScheme.onSurface)
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
            contentPadding = PaddingValues(bottom = 100.dp) // Add padding to the bottom
        ) {
            item {
                RecipeImageHeader(state.value.recipe?.image)
            }

            // Title and Serving Information
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
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            item {
                IngredientList(state.value.recipe?.extendedIngredients)
            }

            item {
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.headlineSmall,
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
            style = MaterialTheme.typography.bodyMedium,
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
    Column(Modifier.padding(16.dp)) {
        ingredients?.forEach { ingredient ->
            if (ingredient != null) {
                Text(
                    text = "- ${ingredient.original}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}