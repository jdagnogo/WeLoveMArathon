package com.jdagnogo.welovemarathon.food.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food

@ExperimentalMaterialApi
@Composable
fun RestaurantContent(
    foods: List<Food>,
    title: String = "Recommended",
    modifier: Modifier = Modifier,
) {
    TitleComponent(title = title, modifier = modifier.padding(8.dp).fillMaxWidth())

    LazyRow(
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(foods) { _, restaurant ->
            RestaurantItem(food = restaurant,
                modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}
