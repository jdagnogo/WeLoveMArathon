package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.tagsTitleStyle

@Composable
fun TeamComponent(
    member: Member,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Image(
            painter = rememberImagePainter(data = member.icon),
            contentDescription = null,
            modifier = Modifier
                .border(width = 1.dp, color = PrimaryLight, shape = CircleShape)
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = member.name,
            style = tagsTitleStyle,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

}