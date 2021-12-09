package com.jdagnogo.welovemarathon.food.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.component.simpleListComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.food.domain.FoodCategory

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun FoodContent(
    state: FoodState,
    onCategorySelected: (FoodCategory) -> Unit,
    modifier: Modifier,
) {
    Surface(modifier = modifier
        .fillMaxSize()
        .animateContentSize()
        .background(WeLoveMarathonTheme.colors.contentBackground)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .statusBarsPadding()
                .background(WeLoveMarathonTheme.colors.contentBackground)
                .fillMaxWidth()) {
            val lazyScope = this
            item {
                val categories = remember { FoodCategory.values() }
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Secondary)
                        .padding(top = 16.dp, start = 8.dp)
                ) {
                    itemsIndexed(categories) { _, category ->
                        FoodCategoryItem(foodCategory = category,
                            onCategorySelected = onCategorySelected)
                    }
                }
                DividerComponent(color = Primary)
            }
            item {
                RestaurantContent(state.recommended, "Recommended (${state.recommended.size})")
            }

            item {
                TitleComponent(title = "Others (${state.others.size})",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth())
                simpleListComponent(
                    state.others.map { it.toSimpleListItem() },
                    lazyScope,
                )
            }
        }
    }
}

@ExperimentalMaterialApi
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
