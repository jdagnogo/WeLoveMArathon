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
    val scale = remember { Animatable(0.5f) }
    val iconAlpha = remember { Animatable(0f) }
    val rotation = remember { Animatable(-30f) }
    val textAlpha = remember { Animatable(0f) }
    val visibleTextLength = remember { androidx.compose.runtime.mutableStateOf(0) }
    val fullText = "We Love Marathon"
    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(
                durationMillis = 250,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        rotation.animateTo(
            targetValue = 10f,
            animationSpec = tween(durationMillis = 100)
        )
        rotation.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 100)
        )
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 150,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                })
        )
        iconAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200))
        textAlpha.animateTo(1f, animationSpec = tween(durationMillis = 150))
        for (i in 1..fullText.length) {
            visibleTextLength.value = i
            delay(20L)
        }
        delay(250L)
        navController.navigate(GlobalDestinations.MainScreen.route) {
            popUpTo(GlobalDestinations.SplashScreen.route) {
                inclusive = true
            }
        }
    }
    SplashScreenContent(
        scale = scale.value,
        iconAlpha = iconAlpha.value,
        rotation = rotation.value,
        textAlpha = textAlpha.value,
        visibleText = fullText.take(visibleTextLength.value),
        modifier = Modifier
    )
}
