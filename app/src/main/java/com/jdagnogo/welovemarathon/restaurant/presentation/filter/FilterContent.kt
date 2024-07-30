@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)

package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.FilterDialogButton
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
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
) {
    var filterApplied = remember { mutableStateOf(state.currentFilterApplied) }
    val scroll = rememberScrollState(0)
    Scaffold(
        topBar = {
            TitleComponent(
                title = stringResource(id = R.string.filter),
                onLeftIconClicked = onBackPressed,
                iconRight = null
            )
        },
        bottomBar = {
            Column {
                DividerComponent(Modifier.fillMaxWidth(), thickness = 2.dp, color = TagColor)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(vertical = MaterialTheme.spacing.small)
                        .padding(horizontal = MaterialTheme.spacing.small)
                ) {
                    FilterDialogButton("Done ! ", {
                        onValidatePressed(filterApplied.value)
                    }, Modifier.weight(1f))
                    FilterDialogButton("Reset", {
                        filterApplied.value = RestaurantAppliedFilter()
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
            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowIconComponent(
                title = stringResource(id = R.string.typeOf),
                data = state.allCategories.map { IconNameFilter(it.name, it.icon) },
                filterApplied = filterApplied.value.typeOfFilters,
                onItemClicked = { result ->
                    val filter = filterApplied.value
                    filterApplied.value = filter.copy(
                        typeOfFilters = filter.typeOfFilters.applyFilter(
                            result.first,
                            result.second
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.cuisine),
                data = state.filter.cuisines,
                modifier = Modifier.padding(horizontal = 16.dp),
                filterApplied = filterApplied.value.cuisines,
                onItemClicked = { result ->
                    val filter = filterApplied.value
                    filterApplied.value = filter.copy(
                        cuisines = filter.cuisines.applyFilter(
                            result.first,
                            result.second
                        )
                    )
                },
            )

            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.plates),
                data = state.filter.plates,
                filterApplied = filterApplied.value.plates,
                onItemClicked = { result ->
                    val filter = filterApplied.value
                    filterApplied.value = filter.copy(
                        plates = filter.plates.applyFilter(
                            result.first,
                            result.second
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )


            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.drinks),
                data = state.filter.drinks,
                filterApplied = filterApplied.value.drinks,
                onItemClicked = { result ->
                    val filter = filterApplied.value
                    filterApplied.value = filter.copy(
                        drinks = filter.drinks.applyFilter(
                            result.first,
                            result.second
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))

            FilterBooleanComponent(
                modifier = Modifier.padding(horizontal = 16.dp),
                isChecked = filterApplied.value.handicapAccess,
                onCheckedChange = {
                    filterApplied.value = filterApplied.value.copy(handicapAccess = it)
                }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.location),
                data = state.filter.location,
                filterApplied = filterApplied.value.location,
                onItemClicked = { result ->
                    val filter = filterApplied.value
                    filterApplied.value = filter.copy(
                        location = filter.location.applyFilter(
                            result.first,
                            result.second
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))

            FilterRowIconComponent(
                title = stringResource(id = R.string.services),
                data = state.filter.services,
                onItemClicked = { result ->
                    val filter = filterApplied.value
                    filterApplied.value = filter.copy(
                        services = filter.services.applyFilter(
                            result.first,
                            result.second
                        )
                    )
                },
                filterApplied = filterApplied.value.services,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))

            FilterPricesComponent(
                modifier = Modifier.padding(horizontal = 16.dp),
                data = state.filter.prices,
                filterApplied = filterApplied.value.prices,
                onItemClicked = { result ->
                    val filter = filterApplied.value
                    filterApplied.value = filter.copy(
                        prices = filter.prices.applyFilter(
                            result.first,
                            result.second
                        )
                    )
                },
            )

            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

/**
 * Will add or remove the [id] in the filter
 */
fun MutableSet<String>.applyFilter(
    id: String,
    isSelected: Boolean
): MutableSet<String> {
    return this.plus(id).takeIf { isSelected }?.toMutableSet()
        ?: this.minus(id).toMutableSet()
}