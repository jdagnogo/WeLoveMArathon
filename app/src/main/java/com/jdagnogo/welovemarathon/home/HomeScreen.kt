package com.jdagnogo.welovemarathon.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier){
        Text(text = "FoodScreen")
    }
}

val TextIconSpacing = 2.dp
val BottomNavHeight = 56.dp
val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
val BottomNavigationItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
