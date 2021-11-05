package com.jdagnogo.welovemarathon.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingComponent(modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Loading...")
        }
    }
}

@Preview
@Composable
fun LoadingComponentPreview() {
    MaterialTheme {
        LoadingComponent(modifier = Modifier)
    }
}
