package com.sammy.paparadoorbell.feature.recipe

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.sammy.paparadoorbell.R
import com.sammy.paparadoorbell.SpoonacularDestination
import com.sammy.paparadoorbell.SpoonacularNavigationActions
import com.sammy.paparadoorbell.ui.theme.mediumfont
import com.sammy.paparadoorbell.ui.theme.regularfont
import com.sammy.paparadoorbell.ui.theme.semiboldfont
import com.sammy.paparadoorbell.ui.theme.ubuntusans

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(
    navActions: SpoonacularNavigationActions,
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit,
) {
    val state = viewModel.uiState.collectAsState()
    val selectedCategory = remember { mutableStateOf("Dessert") }

    LaunchedEffect(selectedCategory.value) {
        viewModel.fetchRecipes(selectedCategory.value)
    }


    Scaffold(
        topBar = {
            TransparentTopAppBar()
        },
        bottomBar = { BottomBar(navActions) }
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
                        .clip(RoundedCornerShape(bottomStart = 43.dp, bottomEnd = 43.dp))
                        .blur(radius = 5.dp)
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 60.dp),
                        text = "What do you want to cook today?",
                        fontSize = 28.sp,
                        fontFamily = semiboldfont,
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
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${selectedCategory.value} Recipe",
                        fontFamily = mediumfont,
                        fontSize = 22.sp
                    )

                }
                if (state.value.isLoading || state.value.isError) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(10) {
                            ShimmerRecipeCardItem()
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(state.value.recipes) { recipe ->
                            RecipeCard(
                                recipe.id,
                                recipe.title,
                                recipe.image,
                                onRecipeClick,
                                onFavoriteClick = { recipeId ->
                                    viewModel.markAsFavoriteRecipe(
                                        recipeId
                                    )
                                }
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
                    fontFamily = ubuntusans,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTopAppBar() {
    var showMenu by remember { mutableStateOf(false) }
    // ViewModel'i al
    val viewModel: RecipeViewModel = hiltViewModel()

    // Room database'den bildirimleri al
    val notifications by viewModel.notifications.collectAsState(initial = listOf())
    val hasNotifications = notifications.isNotEmpty()

    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            BadgedBox(
                badge = {
                    if (hasNotifications) {
                        Badge(
                            modifier = Modifier
                                .size(8.dp)
                                .offset(y = (8).dp, x = (-15).dp), // Adjust dot position
                            backgroundColor = Color(0xFFF4526A)
                        ) { /* Empty content - just the dot */ }
                    }
                }
            ) {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier.width(300.dp)
            ) {
                notifications.forEachIndexed { index, notification ->
                    DropdownMenuItem(
                        text = { Text(text=notification.title ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis) },

                        onClick = {
                            showMenu = false
                        },
                        modifier = Modifier.background(
                            if (index % 2 == 0)   Color.White else Color(0xFFF0BDC4)
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun BottomBar(navActions: SpoonacularNavigationActions) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    BottomNavigation(
        backgroundColor = Color(0xFFF4526A),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home" , tint = Color.White) },
            selected = currentRoute == SpoonacularDestination.RECIPES,
            onClick = { navActions.navigateToRecipe() }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", tint = Color.White) },
            selected = currentRoute == SpoonacularDestination.FAVORITE_RECIPE,
            onClick = { navActions.navigateToFavoriteRecipe() }
        )
    }
}

@Composable
fun CategoryButtons(onCategorySelected: (String) -> Unit) {
    val categories = listOf("Dessert", "Breakfast", "Salad", "Beverage", "Snack", "Main Course", "Side Dish", "Appetizer", "Bread", "Sauce", "Marinade", "Fingerfood", "Drink")
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
    ) {
        // Sol Ok
        if (scrollState.value > 0) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Scroll Left",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 56.dp),
                tint = Color.White

            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 81.dp)
                .horizontalScroll(scrollState),
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
                    Text(
                        text = category,
                        fontFamily = regularfont
                    )
                }
            }
        }

        // Sağ Ok
        if (scrollState.value < scrollState.maxValue) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Scroll Right",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 56.dp),
                tint = Color.White
            )
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
                    .shimmerEffect()
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