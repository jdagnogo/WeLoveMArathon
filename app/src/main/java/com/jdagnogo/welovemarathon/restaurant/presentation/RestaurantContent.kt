package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.CategorySection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.RestaurantSection

@Composable
fun RestaurantContent(
    modifier: Modifier = Modifier,
    state: RestaurantState,
    onMapSelected: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    onLikeClicked: (String) -> Unit = {},
    onCategoryClicked: (String) -> Unit = {},
    onRecommendedSelected: (String) -> Unit = {},
    onRedirectToFilterClicked: () -> Unit = {},
) {
    Scaffold(
        backgroundColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleComponent(
                title = stringResource(id = R.string.food),
                onLeftIconClicked = onBackPressed,
                onRightIconClicked = onMapSelected,

            )

            CategorySection(
                modifier = Modifier.padding(top = 16.dp),
                onCategoryClicked = onCategoryClicked,
                categories = state.allCategories,
                currentSelected = state.currentCategorySelected,
                onRedirectToFilterClicked = onRedirectToFilterClicked,
            )

            DividerComponent(
                modifier = Modifier.padding(top = 16.dp),
                color = Color(0xFFE0E0E0)
            )

            RestaurantSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = state.items,
                onRecommendedSelected = onRecommendedSelected,
                onRedirectToFilterClicked = onRedirectToFilterClicked,
                onLikeClicked = onLikeClicked,
            )
        }
    }
}


@Preview
@Composable
private fun RestaurantContentPreview() {
    MaterialTheme {
        val itemSelected = FoodCategory(
            name = "Ferdinand Huff",
            icon = "litora",
            ordinal = 3
        )
        val state = RestaurantState(
            currentCategorySelected = itemSelected,
            categories = FoodCategory().toFakeFoodCategoryList().plus(itemSelected),
            foods = CategoryItem().toFakeCategoryItemList(),
            items = listOf(

            )

        )
        RestaurantContent(state = state)
    }
}