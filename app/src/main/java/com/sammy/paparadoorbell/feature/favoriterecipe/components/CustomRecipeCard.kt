package com.sammy.paparadoorbell.feature.favoriterecipe.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sammy.paparadoorbell.feature.favoriterecipe.FavoriteRecipeViewModel
import com.sammy.paparadoorbell.ui.theme.regularfont
import com.sammy.paparadoorbell.ui.theme.ubuntusans

@Composable
fun CustomRecipeCard(id: Int, image: String?, name: String?, instruction: String?, viewModel: FavoriteRecipeViewModel) {
    var expanded by remember { mutableStateOf(false) }

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

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector =  Icons.Filled.Delete,
                            contentDescription = "Remove from favorites" ,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    viewModel.markAsUnFavorite(id)
                                },
                            tint = Color.White
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
                        .padding(start = 14.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                        .align(Alignment.TopStart),
                    color = Color.Black,
                    fontFamily = ubuntusans
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = instruction ?: "",
                    style = TextStyle(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(14.dp),
                    color = Color.Black,
                    fontFamily = regularfont
                )
            }
        }
    }
}
