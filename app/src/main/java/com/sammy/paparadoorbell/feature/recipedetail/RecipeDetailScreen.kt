package com.sammy.paparadoorbell.feature.recipedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.sammy.paparadoorbell.data.source.local.entity.LocalRecipes

@Composable
fun RecipeDetailScreen(
    recipe: LocalRecipes,
    recipeId: Int,
    viewModel: RecipeDetailViewModel = hiltViewModel()) {

    val state = viewModel.uiState.collectAsState()
    val ingredients = listOf("egg", "milk", "powder")

    LaunchedEffect(Unit) {
        viewModel.fetchRecipeDetails(recipeId)
    }


    val scrollState = rememberLazyListState()
    val parallaxFactor = 0.5f

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = recipe.image),
            contentDescription = "Recipe Image",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .offset(y = (-scrollState.firstVisibleItemScrollOffset * parallaxFactor).dp)
                .parallaxEffect(scrollState)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            item {
                Spacer(modifier = Modifier.height(200.dp))
            }

            item {
                Text(
                    text = recipe.title ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                Text(
                    text = "Type: cartCurt",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(ingredients ?: emptyList()) { ingredient ->
                Text(
                    text = "- $ingredient",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                Text(
                    text = "bla bla bla bla bla" ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

fun Modifier.parallaxEffect(scrollState: LazyListState, parallaxFactor: Float = 0.5f): Modifier =
    this.then(object : DrawModifier {
        override fun ContentDrawScope.draw() {
            val offset = -scrollState.firstVisibleItemScrollOffset * parallaxFactor
            translate(top = offset) {
                this@draw.drawContent()
            }
        }
    })

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    val dummyRecipe = LocalRecipes(
        id = 1,
        title = "Dummy Recipe",
        image = "https://via.placeholder.com/150",
    )

    RecipeDetailScreen(recipe = dummyRecipe, recipeId = 1)
}