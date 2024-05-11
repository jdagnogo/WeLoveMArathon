package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun RoundedImageComponent(
    modifier: Modifier = Modifier,
    imageModifier: Modifier,
    image: String,
    name: String,
    onImageClicked: (String) -> Unit = {},
) {
    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        modifier = modifier.clickable { onImageClicked(name) }
    ) {
        Image(
            painter = rememberImagePainter(
                data = image,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = name,
            modifier = imageModifier,
            contentScale = ContentScale.Crop,
        )
    }
}