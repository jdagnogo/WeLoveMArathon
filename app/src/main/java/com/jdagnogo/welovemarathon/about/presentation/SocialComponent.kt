package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.about.domain.SocialMedia
import com.jdagnogo.welovemarathon.common.utils.redirectToLink

@Composable
fun SocialComponent(
    modifier: Modifier = Modifier,
    socialMedia: SocialMedia,
) {
    val uriHandler = LocalUriHandler.current
    Image(
        painter = rememberImagePainter(data = socialMedia.icon),
        contentDescription = null,
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
            ) {
                redirectToLink(uriHandler, socialMedia.link)
            }
            .size(50.dp)
    )
}