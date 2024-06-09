package com.sammy.paparadoorbell.feature.favoriterecipe

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteRecipeScreen(
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
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
                                instruction = recipes[index].instructions,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomRecipeCard(id: Int, image: String?, name: String?, instruction: String?, viewModel: FavoriteRecipeViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) } // State for favorite status

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
            .clip(RoundedCornerShape(16.dp))
            .clickable { expanded = !expanded }
            .background(Color.White)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                image?.let {
                    Image(
                        painter = rememberAsyncImagePainter(model = it),
                        contentDescription = "Recipe Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Favorite Icon Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector =  Icons.Filled.Favorite,
                            contentDescription = "Remove from favorites" ,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    viewModel.markAsUnFavorite(id)
                                },
                            tint = Color.Red
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = name ?: "",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = instruction ?: "",
                    style = TextStyle(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}




