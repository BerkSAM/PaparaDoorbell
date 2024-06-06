package com.sammy.paparadoorbell.feature.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.sammy.paparadoorbell.R

@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f),
            contentAlignment = Alignment.Center
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
                Spacer(modifier = Modifier.height(100.dp))
                TextField(value = "",
                    onValueChange = { },
                    placeholder = { Text("Search", fontSize = 14.sp, color = Color.White) },
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .size(350.dp, 50.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(32.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF4526A),
                        unfocusedIndicatorColor = Color(0xFFF4526A),
                        focusedContainerColor = Color(0xFFF4526A),
                        focusedIndicatorColor = Color(0xFFF4526A),
                        disabledIndicatorColor = Color(0xFFF4526A)
                    ),
                    trailingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    })
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Popular Recipe", fontSize = 24.sp, fontWeight = FontWeight.Bold
                )

                Text(text = "View All",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF4526A),
                    modifier = Modifier.clickable { /* Handle click */ })
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier.fillMaxWidth(),
            ) {
                items(state.value.recipes.take(10)) { recipe ->
                    RecipeCard(recipe.id, recipe.title, recipe.image, onRecipeClick)
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipeId: Int, recipeName: String, recipeImage: String, onClick: (Int) -> Unit) {
    Box(modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick(recipeId) }) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = recipeImage),
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .size(175.dp, 150.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Text(text = recipeName)
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun RecipeScreenPreview() {
//    RecipeScreen()
//}
