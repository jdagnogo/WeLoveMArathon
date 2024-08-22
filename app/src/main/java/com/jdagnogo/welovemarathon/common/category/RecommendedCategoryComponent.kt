package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Neutral3
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryItemTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SecondaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun RecommendedCategoryComponent(
    recommendedItems: List<RecommendedCategoryDetails>,
    onRecommendedSelected: (String) -> Unit,
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
                val items = remember {
                    recommendedItems.shuffled()
                }
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(
                        start = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.huge,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(
                        MaterialTheme.spacing.medium,
                        Alignment.CenterHorizontally
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    itemsIndexed(items) { _, category ->
                        RecommendedCategoryContent(
                            item = category,
                            onRecommendedSelected = onRecommendedSelected,

                            )
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendedCategoryContent(
    item: RecommendedCategoryDetails,
    onRecommendedSelected: (String) -> Unit,
    isFavorite: Boolean? = null,
    modifier: Modifier = Modifier,
    onLikeClicked: (String) -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(150.dp)
            .width(150.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onRecommendedSelected(item.id) }
    ) {
        Image(
            painter = rememberImagePainter(
                data = item.images.firstOrNull(),
                builder = {
                    crossfade(true)
                    error(R.drawable.food)
                }
            ),
            contentDescription = item.name,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier.background(Neutral3),
            style = RecommendedCategoryItemTitleStyle,
            text = item.name
        )
        if (isFavorite != null) {
            Icon(
                painter = rememberImagePainter(
                    data = if (isFavorite) R.drawable.fav else R.drawable.ic_fav_unselected,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = "icon",
                tint = if (isFavorite) Secondary else Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .size(MaterialTheme.spacing.large)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                    ) {
                        onLikeClicked(item.id)
                    }
            )
        }
        if (item.isRecommended) {
            Icon(
                painter = rememberImagePainter(
                    data = R.drawable.france ,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                tint = SecondaryLight,
                contentDescription = "icon",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .size(MaterialTheme.spacing.large)
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun RecommendedCategoryContentPreview() {
    val item = RecommendedCategoryDetails(
        "id", "name", "",
        images = listOf(""),
        isRecommended = true,
        bigImages = listOf(""),
    )
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

    MaterialTheme {
        RecommendedCategoryComponent(
            recommendedItems = listOf(RecommendedCategoryDetailsFake), {}
        )
    }
}
