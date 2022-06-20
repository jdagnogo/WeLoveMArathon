package com.jdagnogo.welovemarathon.activities.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jdagnogo.welovemarathon.common.category.CategoryScreen

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ActivitiesContent(
    state: ActivitiesState,
    onItemSelected: (String) -> Unit = {},
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onLikeClicked: (String) -> Unit,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    onFilterClicked: (isVisible: Boolean) -> Unit,
    onRecommendedDialogClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CategoryScreen(
        title = state.currentSelected.name,
        recommendedItems = state.recommendedItems,
        items = state.activitiess,
        tags = state.categoryTags,
        onLikeClicked = onLikeClicked,
        onFiltersSelected = onFiltersSelected,
        modifier = modifier,
        onItemSelected = onItemSelected,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed,
        onFilterClicked = onFilterClicked,
        onResetSelected = onResetSelected,
        shouldDisplayFilter = state.shouldDisplayFilter,
        shouldOpenRecommenderDialog = state.shouldOpenRecommendedDialog,
        shouldOpenFilterDialog = state.shouldOpenFilterDialog,
        onRecommendedDialogClosed = onRecommendedDialogClosed,
        currentRecommended = state.currentActivitiesSelected,
    )
}
