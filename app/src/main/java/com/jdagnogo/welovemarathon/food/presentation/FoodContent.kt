package com.jdagnogo.welovemarathon.food.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.presentation.restaurant.RestaurantContent

@ExperimentalFoundationApi
@Composable
fun FoodContent(
    state: FoodState,
    onCategorySelected: (FoodCategory) -> Unit,
    modifier: Modifier,
) {
    Surface(modifier = modifier
        .fillMaxSize()
        .background(WeLoveMarathonTheme.colors.contentBackground)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .statusBarsPadding()
                .background(WeLoveMarathonTheme.colors.contentBackground)
                .fillMaxWidth()) {
            item {
                RestaurantContent(state.restaurants,
                    "Restaurants",
                    modifier = Modifier.padding(top = 16.dp))
            }

            item {
                RestaurantContent(state.coffees, "Coffees")
            }
            item {
                RestaurantContent(state.deserts, "Deserts")
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview(name = "FoodContent")
@Preview("Dark : FoodContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FoodContentPreview() {
    MaterialTheme {
        val state = FoodState(currentCategory = FoodCategory.RESTAURANT)
        FoodContent(state = state, {}, modifier = Modifier)
    }
}
