package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryTitleStyle
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
            Card(
                backgroundColor = Color(0xFF1E4F7B).copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium,
                modifier = modifier
                    .wrapContentSize()
                    .padding(horizontal = MaterialTheme.spacing.small),
                elevation = 0.dp
            ) {
                Box {
                    Icon(
                        painter = rememberImagePainter(
                            data = R.drawable.star,
                            builder = {
                                crossfade(true)
                                error(R.drawable.ic_wlm_logo)
                            }
                        ),
                        contentDescription = "recommended icon",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.medium)
                            .align(Alignment.TopEnd)
                            .size(22.dp)
                            .background(
                                color = Color(0xFF1E4F7B).copy(alpha = 0.8f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(4.dp)
                    )
                    // Main content
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            style = RecommendedCategoryTitleStyle,
                            text = "Best of",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E4F7B),
                            modifier = Modifier.padding(
                                horizontal = MaterialTheme.spacing.small,
                                vertical =  MaterialTheme.spacing.small
                            )
                        )

                        Box(
                            modifier = Modifier
                                .padding(
                                    top = MaterialTheme.spacing.extraSmall,
                                    bottom = MaterialTheme.spacing.small
                                )
                        ) {
                            val shuffledItems = remember {
                                recommendedItems.shuffled()
                            }
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                                contentPadding = PaddingValues(
                                    start = MaterialTheme.spacing.small,
                                    end = MaterialTheme.spacing.small,
                                ),
                                horizontalArrangement = Arrangement.spacedBy(
                                    MaterialTheme.spacing.small,
                                    Alignment.CenterHorizontally
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                            ) {
                                itemsIndexed(shuffledItems) { _, category ->
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
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        if (item.isRecommended) {
            Icon(
                painter = rememberImagePainter(
                    data = R.drawable.star,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = "recommended icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .size(24.dp)
                    .background(
                        color = Color(0xFF1E4F7B).copy(alpha = 0.8f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(4.dp)
            )
        }

        Text(
            text = item.name,
            style = MaterialTheme.typography.h5.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .widthIn(max = 130.dp)
                .wrapContentWidth()
                .background(
                    color = Color.Black.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
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
                contentDescription = "favorite icon",
                tint = if (isFavorite) Color(0xFF1E4F7B) else Color.Black,
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
