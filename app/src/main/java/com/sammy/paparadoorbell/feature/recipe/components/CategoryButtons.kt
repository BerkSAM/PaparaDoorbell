package com.sammy.paparadoorbell.feature.recipe.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sammy.paparadoorbell.models.CategoryButtonEnum
import com.sammy.paparadoorbell.ui.theme.CustomColors
import com.sammy.paparadoorbell.ui.theme.regularfont

@Composable
fun CategoryButtons(onCategorySelected: (String) -> Unit) {
    val categories = CategoryButtonEnum.entries
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
    ) {
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
                        onCategorySelected(category.toString())
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.PRIMARY_COLOR,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = category.toString(),
                        fontFamily = regularfont
                    )
                }
            }
        }

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