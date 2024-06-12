package com.sammy.paparadoorbell.feature.recipedetail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sammy.paparadoorbell.ui.theme.regularfont
import com.sammy.paparadoorbell.utils.stripHtml

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
            text = instructions.stripHtml(),
            fontSize = 12.sp,
            fontFamily = regularfont,
            modifier = Modifier.padding(16.dp)
        )
    }
}