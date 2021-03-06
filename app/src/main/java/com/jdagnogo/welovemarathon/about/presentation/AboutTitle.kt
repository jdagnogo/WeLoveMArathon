package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenTitle

@Composable
fun AboutTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        text = title,
        style = emptyScreenTitle,
        textAlign = TextAlign.Justify,
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    )
}