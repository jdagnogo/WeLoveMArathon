@file:OptIn(ExperimentalFoundationApi::class)

package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryContent
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant

@Composable
fun RestaurantSection(
    modifier: Modifier = Modifier,
    items: List<Restaurant>,
    onRecommendedSelected: (String) -> Unit,
    onRedirectToFilterClicked: () -> Unit = {},
) {
    if (items.isEmpty()) {
        RestaurantEmptySection(modifier, onRedirectToFilterClicked = onRedirectToFilterClicked)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                end = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.large,
                bottom = MaterialTheme.spacing.large,
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = modifier
        ) {
            itemsIndexed(items) { _, restaurant ->
                RecommendedCategoryContent(
                    modifier = Modifier.animateItemPlacement(
                        tween(durationMillis = 250)
                    ),
                    item = restaurant.toRecommendedCategoryDetails(),
                    onRecommendedSelected = onRecommendedSelected
                )
            }
        }
    }
}