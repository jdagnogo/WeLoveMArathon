package com.jdagnogo.welovemarathon.common.submenu

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.ActivityTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SubMenuComponent(
    items: List<SubMenuItem>,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.huge,
                end = MaterialTheme.spacing.huge,
                top = MaterialTheme.spacing.large,
                bottom = MaterialTheme.spacing.extraHuge
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.animateContentSize()
        ) {
            items(items.size) { index ->
                val item = items[index]
                SubMenuGridComponent(
                    item = item,
                    onItemClicked = onItemSelected,
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SubMenuGridComponent(
    item: SubMenuItem,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        onClick = { onItemClicked(item.ordinal) },
    ) {
        val iconSize = MaterialTheme.spacing.extraHuge
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(
                    bottom = MaterialTheme.spacing.medium,
                    top = MaterialTheme.spacing.medium,
                )
        ) {
            Image(
                painter = rememberImagePainter(
                    data = if (item.iconUrl.isNotEmpty()) item.iconUrl else item.icon,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = item.title,
                modifier = Modifier
                    .size(iconSize)
            )
            Text(
                text = item.title,
                style = ActivitySubTitleStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small)
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun SubMenuComponentPreview() {
    val subMenuItems = SubMenuShopping.values()
    MaterialTheme {
        SubMenuComponent(items = subMenuItems.toList().map { it.subMenuItem }, {})
    }
}
