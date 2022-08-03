package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.common.ui.theme.*
import com.jdagnogo.welovemarathon.wine.domain.Wine

@Composable
fun WineComponent(
    items: List<Wine>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.huge,
        ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier.animateContentSize()
    ) {
        items(items.size) { index ->
            val item = items[index]
            WineItemComponent(item = item)
        }
    }
}

@Composable
fun WineItemComponent(modifier: Modifier = Modifier, item: Wine) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberImagePainter(data = item.icon),
            contentDescription = null,
            modifier = modifier
                .width(150.dp)
                .height(300.dp),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = item.name,
            style = emptyScreenTitle,
            textAlign = TextAlign.Center,
            fontSize =12.sp ,
            color = TagColor,
            modifier = Modifier.width(150.dp),
        )
    }
}