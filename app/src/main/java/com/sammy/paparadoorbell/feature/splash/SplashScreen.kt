package com.sammy.paparadoorbell.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sammy.paparadoorbell.R
import com.sammy.paparadoorbell.ui.theme.CustomColors
import com.sammy.paparadoorbell.ui.theme.meriandamedium
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        //Waiting for Lottie to appear.
        delay(2000)
        onSplashFinished()
    }

    Scaffold(
        containerColor = CustomColors.PRIMARY_COLOR,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(R.raw.splashlottie)
            )
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )

            Text(
                text = "Welcome To Recipara",
                fontFamily = meriandamedium,
                style = TextStyle(color = Color.White, fontSize = 24.sp),
                modifier = Modifier.padding(top = 50.dp)
            )
        }
    }
}