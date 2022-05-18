package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jdagnogo.welovemarathon.common.type_two.TypeTwoComponent


@Composable
fun BeachesBarContent(
    state: BeachState,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    onFilterClicked: (isVisible: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    TypeTwoComponent(
        title = state.currentSelected.name,
        item = state.typeTwoItem,
        categoryItems = state.categories,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed,
        onFilterClicked = onFilterClicked,
        modifier = modifier,
    )
}
