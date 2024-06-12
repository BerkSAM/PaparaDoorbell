package com.sammy.paparadoorbell.feature.recipedetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sammy.paparadoorbell.ui.theme.regularfont

@Composable
fun IngredientList(ingredients: List<com.sammy.paparadoorbell.data.source.network.response.recipesDetail.ExtendedIngredient?>?) {
    Column(Modifier.padding(8.dp)) {
        ingredients?.chunked(3)
            ?.forEach { rowIngredients -> // Adjust the chunk size based on how many items you want per row
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between items
                ) {
                    rowIngredients.forEach { ingredient ->
                        if (ingredient != null) {
                            Box(
                                modifier = Modifier
                                    .size(
                                        110.dp,
                                        150.dp
                                    ) // Set the fixed size with height of 130.dp
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(8.dp)
                                    )
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
