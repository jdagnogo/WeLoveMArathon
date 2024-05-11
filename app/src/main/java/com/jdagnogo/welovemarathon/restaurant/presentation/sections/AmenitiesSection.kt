package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.restaurant.domain.Amenities

fun LazyListScope.amenitiesSection(
    modifier: Modifier = Modifier,
    amenities: List<Amenities>,
) {
    if (amenities.isNotEmpty()) {
        item("Amenitie title") {
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.amenitiesTitle),
                style = SubTitleStyle.copy(fontSize = 18.sp),
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }

        items(amenities, key = { it.type }) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = rememberImagePainter(
                        data = it.icon,
                        builder = {
                            crossfade(true)
                            error(R.drawable.ic_wlm_logo)
                        }
                    ),
                    contentDescription = it.type,
                    tint = Color.White,
                    modifier = Modifier
                        .height(MaterialTheme.spacing.semiHuge)
                        .padding(end = 8.dp)
                )

                Text(text = "${it.type}:", style = descriptionStyle.copy(color = TagColor))
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    text = it.description,
                    overflow = TextOverflow.Ellipsis,
                    style = descriptionStyle
                )
            }
        }
    }
}