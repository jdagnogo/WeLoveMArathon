package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.common.ui.theme.*

@Composable
fun TeamComponent(
    members: List<Member>,
    modifier: Modifier = Modifier
) {
    AboutTitle(title = "Our team")

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.huge,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.medium,
            Alignment.CenterHorizontally
        ),
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .animateContentSize()
    ) {
        itemsIndexed(members) { _, member ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberImagePainter(data = member.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .border(width = 1.dp, color = PrimaryLight, shape = CircleShape)
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit,
                )

                Text(
                    text = member.name,
                    style = tagsTitleStyle,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

        }
    }
}