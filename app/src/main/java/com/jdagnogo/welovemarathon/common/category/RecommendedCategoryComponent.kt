package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Neutral3
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryItemTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun RecommendedCategoryComponent(
    recommendedItems: List<RecommendedCategoryItem>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(recommendedItems.isNotEmpty()) {
        Column(modifier = modifier) {
            Text(
                style = RecommendedCategoryTitleStyle,
                text = "Best of",
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.huge)
            )
            Box(modifier = Modifier.padding(top = MaterialTheme.spacing.medium)) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(
                        start = MaterialTheme.spacing.huge,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = MaterialTheme.spacing.huge)
                        .animateContentSize()
                ) {
                    itemsIndexed(recommendedItems) { _, category ->
                        RecommendedCategoryContent(item = category, onItemSelected = onItemSelected)
                    }
                }

                Icon(
                    painterResource(id = R.drawable.ic_forward),
                    contentDescription = "Swipe to right",
                    tint = Color.White,
                    modifier = Modifier
                        .size(MaterialTheme.spacing.huge)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun RecommendedCategoryContent(
    item: RecommendedCategoryItem,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(150.dp)
            .width(150.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onItemSelected(item.id) }
    ) {
        Image(
            painter = rememberImagePainter(
                data = item.image,
                builder = {
                    crossfade(true)
                    error(R.drawable.food)
                }
            ),
            contentDescription = item.name,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = Modifier.background(Neutral3),
            style = RecommendedCategoryItemTitleStyle,
            text = item.name
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun RecommendedCategoryContentPreview() {
    val item = RecommendedCategoryItem("id", "name", "")
    MaterialTheme {
        RecommendedCategoryContent(
            item = item, {}
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun RecommendedCategoryComponentPreview() {
    val items = listOf(
        RecommendedCategoryItem("id", "name", ""),
        RecommendedCategoryItem("id2", "name2", ""),
        RecommendedCategoryItem("id3", "name3", ""),
        RecommendedCategoryItem("id4", "name4", ""),
        RecommendedCategoryItem("id5", "name5", ""),
    )
    MaterialTheme {
        RecommendedCategoryComponent(
            recommendedItems = items, {}
        )
    }
}