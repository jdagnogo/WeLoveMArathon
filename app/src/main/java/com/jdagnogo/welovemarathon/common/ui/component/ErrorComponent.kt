package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorComponent() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.Red)
            .fillMaxWidth()) {
        Text(color = Color.White,
            text = "No internet")
    }
}
