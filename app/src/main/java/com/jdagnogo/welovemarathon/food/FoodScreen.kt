package com.jdagnogo.welovemarathon.food

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FoodScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier){
        Text(text = "FoodScreen")
    }
}