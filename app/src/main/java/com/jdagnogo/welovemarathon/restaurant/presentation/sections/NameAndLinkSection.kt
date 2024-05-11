package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SplashScreenTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink


fun LazyListScope.nameAndLinkSection(
    modifier: Modifier = Modifier,
    uriHandler: UriHandler,
    website: String,
    name: String,
) {
    item {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    redirectToLink(uriHandler, website)
                }
                .padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            Text(
                overflow = TextOverflow.Ellipsis,
                style = SplashScreenTitleStyle.copy(fontSize = 24.sp),
                maxLines = 1,
                text = name,
            )
            if (website.isNotEmpty()) {
                Icon(
                    painterResource(id = R.drawable.ic_link),
                    contentDescription = "back",
                    tint = Secondary,
                    modifier = modifier
                        .padding(start = MaterialTheme.spacing.extraMedium)
                        .size(MaterialTheme.spacing.medium)
                )
            }
        }
    }
}