package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.CategoryTag
import com.jdagnogo.welovemarathon.common.category.FilterDialogButton
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.restaurant.domain.IconNameFilter
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantFilter
import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantState

@Composable
fun FilterContent(
    modifier: Modifier = Modifier,
    state: RestaurantState,
    onBackPressed: () -> Unit = {},
    onValidatePressed: () -> Unit = {},
    onResetPressed: () -> Unit = {},
) {
    val filterApplied = remember(state.currentFilterApplied) { state.currentFilterApplied }
    val scroll = rememberScrollState(0)
    Scaffold(
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.medium)
                    .padding(horizontal = MaterialTheme.spacing.small)
            ) {
                FilterDialogButton("Done ! ", {
                    onValidatePressed()
                }, Modifier.weight(1f))
                FilterDialogButton("Reset", {
                    onResetPressed()
                }, Modifier.weight(1f))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(scroll)
        ) {
            TitleComponent(
                title = stringResource(id = R.string.filter),
                onLeftIconClicked = onBackPressed,
                iconRight = null
            )

            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.typeOf),
                style = SubTitleStyle.copy(fontSize = 18.sp),
            )

            LazyRow {
                items(state.allCategories, key = { it.name }) {category->
                    TypeOfItem(
                        icon = category.icon,
                        name = category.name,
                        isSelected = filterApplied.typeOfFilters.contains(category.name),
                        onItemClicked = { filterApplied.typeOfFilters.plus(category.name)}
                    )
                }
            }
        }
    }
}