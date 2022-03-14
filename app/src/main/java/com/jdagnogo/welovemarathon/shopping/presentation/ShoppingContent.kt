package com.jdagnogo.welovemarathon.shopping.presentation

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
fun ShoppingContent(
    state: ShoppingState,
    onItemSelected: (String) -> Unit = {},
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onFilterClicked: (isVisible: Boolean) -> Unit,
    onRecommendedDialogClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CategoryScreen(
        title = state.subMenuUiModel.screenName,
        recommendedItems = state.recommendedItems,
        items = state.shoppings,
        tags = state.categoryTags,
        onFiltersSelected = onFiltersSelected,
        modifier = modifier,
        onItemSelected = onItemSelected,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed,
        onFilterClicked = onFilterClicked,
        shouldOpenRecommenderDialog = state.shouldOpenRecommendedDialog,
        shouldOpenFilterDialog = state.shouldOpenFilterDialog,
        onRecommendedDialogClosed = onRecommendedDialogClosed,
        currentShoppingSelected = state.currentShoppingSelected,
    )
}
