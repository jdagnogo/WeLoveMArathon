package com.jdagnogo.welovemarathon.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.GlobalDestinations
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(500L)
        navController.navigate(GlobalDestinations.MainScreen.route) {
            popUpTo(GlobalDestinations.SplashScreen.route) {
                inclusive = true
            }
        }
    }
    SplashScreenContent(scale = scale.value, modifier = Modifier)
}
