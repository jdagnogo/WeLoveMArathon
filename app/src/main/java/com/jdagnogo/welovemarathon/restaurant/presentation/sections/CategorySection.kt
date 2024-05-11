package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodCategory.Companion.allCategory


@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    categories: List<FoodCategory>,
    currentSelected: FoodCategory,
    onCategoryClicked: (String) -> Unit = {},
) {
    Log.d("toto", "current selecte : " + currentSelected)
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.huge,
            end = MaterialTheme.spacing.huge,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.medium,
            Alignment.CenterHorizontally
        ),
    ) {
        itemsIndexed(
            items = categories,
            key = { _: Int, item: FoodCategory -> item.name }
        ) { _, item ->
            RestaurantCategoryItem(
                item = item,
                isSelected = currentSelected.name == item.name,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
}


@Composable
private fun RestaurantCategoryItem(
    modifier: Modifier = Modifier,
    item: FoodCategory,
    isSelected: Boolean,
    onCategoryClicked: (String) -> Unit = {},
) {
    val color by animateColorAsState(
        if (isSelected) Secondary else Color.White,
        label = "iconColor"
    )
    val interactionSource = remember { MutableInteractionSource() }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onCategoryClicked(item.name) }) {
        Image(
            painter = rememberImagePainter(
                data = item.icon.ifEmpty { item.icon },
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            colorFilter = ColorFilter.tint(color),
            contentDescription = item.name,
            modifier = Modifier.size(40.dp)
        )
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = item.name,
            color = color,
            style = ActivitySubTitleStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (isSelected) {
            DividerComponent(thickness = 4.dp, color = Color.White)
        }
    }
}

@Preview
@Composable
private fun RestaurantCategoryItemSelected() {
    MaterialTheme {
        RestaurantCategoryItem(
            modifier = Modifier, item = allCategory, isSelected = true, onCategoryClicked = {}

        )
    }
}

@Preview
@Composable
private fun RestaurantCategoryItem() {
    MaterialTheme {
        RestaurantCategoryItem(
            modifier = Modifier, item = allCategory, isSelected = false, onCategoryClicked = {}

        )
    }
}