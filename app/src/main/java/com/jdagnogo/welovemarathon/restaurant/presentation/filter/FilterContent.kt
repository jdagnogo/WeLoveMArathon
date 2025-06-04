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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
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
) {
    var filterApplied = remember { mutableStateOf(state.currentFilterApplied) }
    val scroll = rememberScrollState(0)
    val primaryBlue = Color(0xFF1E4F7B)

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
                DividerComponent(
                    Modifier.fillMaxWidth(), 
                    thickness = 2.dp, 
                    color = primaryBlue
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(vertical = MaterialTheme.spacing.small)
                        .padding(horizontal = MaterialTheme.spacing.small)
                ) {
                    FilterDialogButton(
                        "Done",
                        onClick = {
                            onValidatePressed(filterApplied.value)
                        },
                        modifier = Modifier.weight(1f),
                        backgroundColor = primaryBlue
                    )
                    FilterDialogButton(
                        "Reset",
                        onClick = {
                            filterApplied.value = RestaurantAppliedFilter()
                        },
                        modifier = Modifier.weight(1f),
                        backgroundColor = primaryBlue
                    )
                }
            }
        },
        backgroundColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(scroll)
                .background(Color.White)
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
                data = state.filter?.cuisines?: emptyList(),
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
                data = state.filter?.plates?: emptyList(),
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
                data = state.filter?.drinks?: emptyList(),
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

            Spacer(modifier = Modifier.padding(8.dp))

            FilterBooleanComponent(
                modifier = Modifier.padding(horizontal = 16.dp),
                isChecked = filterApplied.value.evCharger,
                title = R.string.ev_charger,
                icon = R.drawable.ev_charger,
                onCheckedChange = {
                    filterApplied.value = filterApplied.value.copy(evCharger = it)
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            FilterRowComponent(
                title = stringResource(id = R.string.location),
                data = state.filter?.location?: emptyList(),
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
                data = state.filter?.services?: emptyList(),
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
                data = state.filter?.prices?: emptyList(),
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

@Composable
private fun FilterDialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF1E4F7B)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White
        ),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .height(48.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = Color.White
        )
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