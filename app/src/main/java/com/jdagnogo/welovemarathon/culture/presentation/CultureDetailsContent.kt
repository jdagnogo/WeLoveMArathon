package com.jdagnogo.welovemarathon.culture.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jdagnogo.welovemarathon.common.type_two.TypeTwoComponent
import com.jdagnogo.welovemarathon.culture.presentation.CultureState


@Composable
fun CulturesDetailsContent(
    state: CultureState,
    onLikeClicked: (String) -> Unit,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    TypeTwoComponent(
        title = state.currentSelected.name,
        item = state.typeTwoItem,
        categoryItems = state.categories,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed,
        onFilterClicked = {},
        onLikeClicked = onLikeClicked,
        shouldDisplayFilter = false,
        shouldOpenFilterDialog =false,
        tags = emptyList(),
        onResetSelected = {},
        onFiltersSelected = {},
        onDismissFilterRequest= {},
        modifier = modifier,
    )
}
