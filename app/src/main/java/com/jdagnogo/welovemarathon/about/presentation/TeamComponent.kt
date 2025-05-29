package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.tagsTitleStyle
import com.jdagnogo.welovemarathon.common.utils.redirectToLink

@Composable
fun TeamComponent(
    member: Member,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(data = member.icon),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                ) {
                    redirectToLink(uriHandler, member.link)
                }
                .border(width = 1.dp, color = PrimaryLight, shape = CircleShape)
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = member.name,
            style = tagsTitleStyle.copy(color = Color(0xFF1E4F7B)),
            modifier = Modifier.padding(top = 8.dp)
        )
    }

}