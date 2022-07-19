@file:OptIn(ExperimentalFoundationApi::class)

package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R

@Composable
fun PhotosComponent(
    modifier: Modifier = Modifier,
    photo: String,
) {
    Image(
        painter = rememberImagePainter(
            data = photo, builder = {
                crossfade(true)
                error(R.drawable.food)
            }
        ),
        contentDescription = null,
        modifier = modifier
            .heightIn(min = 0.dp, max = 300.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}