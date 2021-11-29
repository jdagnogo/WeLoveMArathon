package com.jdagnogo.welovemarathon.food.presentation.restaurant

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.food.domain.restaurant.Restaurant
import com.jdagnogo.welovemarathon.food.presentation.FoodContent
import com.jdagnogo.welovemarathon.food.presentation.FoodState
import com.jdagnogo.welovemarathon.run.presentation.RunTitleComponent

@Composable
fun RestaurantContent(restaurants: List<Restaurant>, modifier: Modifier = Modifier) {
    RunTitleComponent(title = "Recommended", alignRight = true, modifier = Modifier)

    LazyRow(
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(restaurants) { _, restaurant ->
            RestaurantItem(restaurant = restaurant)
        }
    }

    DividerComponent(color = Primary)
    RunTitleComponent(title = "Others", alignRight = false, modifier = Modifier)
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview(name = "FoodContent")
@Preview("Dark : FoodContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FoodContentPreview() {
    MaterialTheme {
        val state = FoodState()
        FoodContent(state = state, {}, modifier = Modifier)
    }
}
