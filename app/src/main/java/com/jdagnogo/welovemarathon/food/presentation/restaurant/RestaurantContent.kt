package com.jdagnogo.welovemarathon.food.presentation.restaurant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleIconComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food

@Composable
fun RestaurantContent(
    foods: List<Food>,
    title: String = "Recommended",
    modifier: Modifier = Modifier,
) {
    TitleIconComponent(title = title, modifier = Modifier)

    LazyRow(
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(foods) { _, restaurant ->
            RestaurantItem(food = restaurant)
        }
    }
    DividerComponent(color = Primary)
}
