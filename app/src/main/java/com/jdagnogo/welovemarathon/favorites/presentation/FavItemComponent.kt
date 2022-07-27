package com.jdagnogo.welovemarathon.favorites.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.like.domain.Favorite
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryItemTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.tagsTitleStyle
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@ExperimentalMaterialApi
@Composable
fun FavItemsComponent(
    modifier: Modifier = Modifier,
    items: List<Favorite>
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.small,
            end = MaterialTheme.spacing.huge,
            top = MaterialTheme.spacing.large,
            bottom = MaterialTheme.spacing.large,
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier.animateContentSize()
    ) {
        items(items.size) { index ->
            val item = items[index]
            FavItem(item = item)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FavItem(
    item: Favorite,
    modifier: Modifier = Modifier,
) {
    val scroll = rememberScrollState(0)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = rememberImagePainter(
                data = item.parentIcon,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = "icon",
            tint = Color.White,
            modifier = Modifier.size(MaterialTheme.spacing.semiHuge)
        )
        Card(
            elevation = MaterialTheme.spacing.small,
            shape = MaterialTheme.shapes.large,
            backgroundColor = PrimaryLight,
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.small)
                .weight(1f)
        ) {
            Column(Modifier.padding(MaterialTheme.spacing.small)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = RecommendedCategoryItemTitleStyle,
                        text = item.name
                    )
                    val uriHandler = LocalUriHandler.current
                    val context = LocalContext.current
                    ContactComponent(
                        modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                        icon = R.drawable.location,
                        iconSize = 24.dp,
                        onClicked = { redirectToLink(uriHandler, item.locationLink) },
                    )
                    ContactComponent(
                        modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                        icon = R.drawable.ic_phone,
                        iconSize = 24.dp,
                        onClicked = {
                            redirectToPhone(context, item.number)
                        },
                    )
                }
                Divider(color = Color.White, thickness = 1.dp)
                Text(
                    overflow = TextOverflow.Ellipsis,
                    style = tagsTitleStyle,
                    text = item.tags,
                    maxLines = 1,
                    modifier = Modifier.horizontalScroll(scroll)
                        .padding(
                            top = MaterialTheme.spacing.medium
                        )
                        .padding(
                            horizontal = MaterialTheme.spacing.small
                        )
                )
            }
        }
    }
}