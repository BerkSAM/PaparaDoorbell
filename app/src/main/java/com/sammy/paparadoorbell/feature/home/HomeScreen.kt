package com.sammy.paparadoorbell.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sammy.paparadoorbell.R
import com.sammy.paparadoorbell.ui.theme.boldfont
import com.sammy.paparadoorbell.ui.theme.mediumfont


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navRecipes: () -> Unit) {
    Scaffold {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val halfHeight = this.maxHeight / 2
            Image(
                painter = painterResource(id = R.drawable.homescreenbg),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop

            )
            Column {
                Box(modifier = Modifier.height(halfHeight)) {

                }
                Box(modifier = Modifier.height(halfHeight), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "What do you want to cook today?",
                            fontFamily = boldfont,
                            fontSize = 33.sp,
                            color = Color.White,
                            lineHeight = 50.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 90.dp),
                            text = "Let's make a delicious dish with " +
                                    "the best recipe for you!",

                            fontFamily = mediumfont,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(65.dp))
                        Button(
                            modifier = Modifier.size(250.dp, 50.dp), onClick = {
                                navRecipes()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4526A))
                        ) {
                            Text(fontSize = 20.sp, fontWeight = FontWeight.Bold, text = "Start")
                        }
                    }
                }
            }
        }
    }
}