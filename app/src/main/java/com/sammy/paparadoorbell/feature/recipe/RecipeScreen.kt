package com.sammy.paparadoorbell.feature.recipe

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.sammy.paparadoorbell.R
import com.sammy.paparadoorbell.SpoonacularNavigationActions

enum class Tab {
    HOME,
    FAVORITE
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(
    navActions: SpoonacularNavigationActions,
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit,
) {
    val state = viewModel.uiState.collectAsState()

    val selectedTab = remember { mutableStateOf(Tab.HOME) }
    val selectedCategory = remember { mutableStateOf("Dessert") }

    LaunchedEffect(selectedTab.value) {
        when (selectedTab.value) {
            Tab.HOME -> viewModel.fetchRecipes()
            Tab.FAVORITE -> viewModel.fetchFavoriteRecipes()
        }
    }

    LaunchedEffect(selectedCategory.value) {
        viewModel.fetchRecipes(selectedCategory.value)
    }


    Scaffold(
        //modifier = Modifier.statusBarsPadding(),
        bottomBar = { BottomBar(selectedTab, navActions) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.21f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.recipedonuts),
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 115.dp, bottomEnd = 115.dp))
                        .blur(radius = 5.dp)
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 80.dp),
                        text = "What do you want to cook today?",
                        fontSize = 28.sp,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CategoryButtons { category ->
                        selectedCategory.value = category
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${selectedCategory.value} Recipe",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(text = "View All",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF4526A),
                        modifier = Modifier.clickable { /* Handle click */ })
                }
                if (state.value.isLoading || state.value.isError) {
                    Log.d("RecipeScreen", "Loading: $state.value.isLoading")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(10) { // Placeholder items for the shimmer effect
                        ShimmerRecipeCardItem() // Use ShimmerRecipeCardItem composable
                    }
                }
                } else {
                    Log.d("RecipeScreen", "Loading: $state.value.isLoading")
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(state.value.recipes.take(10)) { recipe ->
                            RecipeCard(
                                recipe.id,
                                recipe.title,
                               recipe.image,
                                onRecipeClick,
                                onFavoriteClick = { recipeId -> viewModel.markAsFavoriteRecipe(recipeId) }
                            )
                        }
                    }
               }
            }
        }
    }
}



@Composable
fun RecipeCard(
    recipeId: Int,
    recipeName: String,
    recipeImage: String,
    onClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(recipeId) }
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = recipeImage),
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = recipeName,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun BottomBar(selectedTab: MutableState<Tab>, navActions: SpoonacularNavigationActions) {
    BottomNavigation(
        backgroundColor = Color.LightGray,
        contentColor = Color(0xFFF4526A)
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    tint = Color(0xFFF4526A)
                )
            },
            selected = selectedTab.value == Tab.HOME,
            onClick = { selectedTab.value = Tab.HOME }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.fav),
                    contentDescription = "Favorite",
                    tint = Color(0xFFF4526A)
                )
            },
            selected = selectedTab.value == Tab.FAVORITE,
            onClick = {
                selectedTab.value = Tab.FAVORITE
                navActions.navigateToFavoriteRecipe()
            }
        )
    }
}

@Composable
fun CategoryButtons(onCategorySelected: (String) -> Unit) {
    val categories = listOf("Dessert", "Breakfast", "Salad", "Beverage", "Snack")
    Row(
        modifier = Modifier
            .padding(start = 86.dp, end = 86.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        categories.forEachIndexed { index, category ->
            if (index != 0) {
                Spacer(modifier = Modifier.width(8.dp))
            }
            Button(
                onClick = {
                    onCategorySelected(category)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4526A),
                    contentColor = Color.White
                )
            ) {
                Text(text = category)
            }
        }
    }
}




@Composable
fun ShimmerRecipeCardItem() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .shimmerEffect() // Apply shimmer effect to the image placeholder
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
                    .shimmerEffect() // Apply shimmer effect to the text placeholder
            )
        }
    }
}

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    // Return a modifier that applies the brush
    this.then(
        Modifier.background(
            Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.9f), // Use alpha for better visibility
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 0.9f)
                ),
                start = Offset(10f, 10f),
                end = Offset(translateAnim.value, translateAnim.value)
            )
        )
    )
}