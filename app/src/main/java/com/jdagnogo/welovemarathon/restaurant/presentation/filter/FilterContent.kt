@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)

package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.FilterDialogButton
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.restaurant.domain.IconNameFilter
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantAppliedFilter
import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantState

@Composable
fun FilterContent(
    modifier: Modifier = Modifier,
    state: RestaurantState,
    onBackPressed: () -> Unit = {},
    onValidatePressed: (RestaurantAppliedFilter) -> Unit = {},
    onResetPressed: () -> Unit = {},
) {
    val filterApplied = remember(state.currentFilterApplied) { state.currentFilterApplied }
    val scroll = rememberScrollState(0)
    Scaffold(
        bottomBar = {
            Column {
                DividerComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.spacing.medium)
                        .padding(horizontal = MaterialTheme.spacing.small)
                ) {
                    FilterDialogButton("Done ! ", {
                        onValidatePressed(filterApplied)
                    }, Modifier.weight(1f))
                    FilterDialogButton("Reset", {
                        onResetPressed()
                    }, Modifier.weight(1f))
                }
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
            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowIconComponent(
                title = stringResource(id = R.string.typeOf),
                data = state.allCategories.map { IconNameFilter(it.name,it.icon) },
                filterApplied = state.currentFilterApplied.typeOfFilters,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.cuisine),
                data = state.filter.cuisines,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.plates),
                data = state.filter.plates,
                modifier = Modifier.padding(horizontal = 16.dp)
            )


            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.drinks),
                data = state.filter.drinks,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowIconComponent(
                title = stringResource(id = R.string.services),
                data = state.filter.services,
                filterApplied = state.currentFilterApplied.services,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}