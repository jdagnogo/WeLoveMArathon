package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val state by homeViewModel.state.collectAsState()
    HomeContent(state = state, modifier = modifier)
}

val TextIconSpacing = 2.dp
val BottomNavHeight = 56.dp
val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
val BottomNavigationItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
