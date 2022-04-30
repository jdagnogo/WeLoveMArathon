package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


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

}
