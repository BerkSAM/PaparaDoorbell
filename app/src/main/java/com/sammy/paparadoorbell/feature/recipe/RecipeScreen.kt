package com.sammy.paparadoorbell.feature.recipe

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sammy.paparadoorbell.R
import com.sammy.paparadoorbell.SpoonacularNavigationActions
import com.sammy.paparadoorbell.feature.recipe.components.CategoryButtons
import com.sammy.paparadoorbell.feature.recipe.components.RecipeCard
import com.sammy.paparadoorbell.models.CategoryButtonEnum
import com.sammy.paparadoorbell.ui.componentes.BottomBar
import com.sammy.paparadoorbell.ui.componentes.ShimmerRecipeCardItem
import com.sammy.paparadoorbell.ui.componentes.TransparentTopAppBar
import com.sammy.paparadoorbell.ui.theme.mediumfont
import com.sammy.paparadoorbell.ui.theme.semiboldfont

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(
    navActions: SpoonacularNavigationActions,
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit,
) {
    val state = viewModel.uiState.collectAsState()
    val selectedCategory = viewModel.selectedCategory.collectAsState()



    LaunchedEffect(selectedCategory.value) {
        viewModel.fetchRecipes(selectedCategory.value.toString())
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
                        viewModel.setSelectedCategory(CategoryButtonEnum.valueOf(category))
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
                                onRecipeClick
                            )
                        }
                    }
                }
            }
        }
    }
}




