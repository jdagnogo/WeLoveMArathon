package com.jdagnogo.welovemarathon.food.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
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
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(WeLoveMarathonTheme.colors.contentBackground)) {

            val categories = remember { FoodCategory.values() }
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .statusBarsPadding()
            ) {
                itemsIndexed(categories) { _, category ->
                    FoodCategoryItem(foodCategory = category,
                        onCategorySelected = onCategorySelected)
                }
            }
            DividerComponent(color = Primary)

            when (state.currentCategory) {
                FoodCategory.RESTAURANT -> {
                    RestaurantContent(state.restaurants)
                }
                else ->{}
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