package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.Shapes
import com.jdagnogo.welovemarathon.common.ui.theme.White
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant

@Composable
fun BottomBarSection(
    modifier: Modifier = Modifier,
    uriHandler: UriHandler,
    context: Context,
    restaurant: Restaurant,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min)
            .padding(horizontal = 4.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clip(Shapes.large)
            .border(width = 1.dp, color = Secondary, shape = Shapes.large)
            .background(White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactComponent(
            modifier = Modifier
                .weight(1f)
                .padding(MaterialTheme.spacing.extraSmall),
            icon = R.drawable.location,
            textColor = PrimaryDark,
            iconSize = 24.dp,
            text = restaurant.location,
            onClicked = { redirectToLink(uriHandler, restaurant.locationLink) },
        )
        Divider(
            modifier = Modifier.fillMaxHeight().width(1.dp),
            color = Secondary,
            thickness = 1.dp,
        )
        ContactComponent(
            modifier = Modifier
                .weight(1f)
                .padding(MaterialTheme.spacing.extraSmall),
            icon = R.drawable.ic_phone,
            text = restaurant.number,
            textColor = PrimaryDark,
            iconSize = 24.dp,
            onClicked = {
                redirectToPhone(context, restaurant.number)
            },
        )
    }

}