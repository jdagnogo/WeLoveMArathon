package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun CategoryScreen(
    title: String,
    recommendedItems: List<RecommendedCategoryDetails>,
    items: List<CategoryItem>,
    tags: List<CategoryTag>,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    currentRecommended: RecommendedCategoryDetails?,
    shouldOpenRecommenderDialog: Boolean,
    shouldDisplayFilter: Boolean,
    shouldOpenFilterDialog: Boolean,
    onItemSelected: (String) -> Unit,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onFilterClicked: (isVisible: Boolean) -> Unit,
    onRecommendedDialogClosed: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Primary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleComponent(
            title = title,
            onLeftIconClicked = onBackPressed,
            onRightIconClicked = onMapSelected
        )

        RecommendedCategoryComponent(
            recommendedItems = recommendedItems,
            onRecommendedSelected = onItemSelected,
            modifier = Modifier.padding(top = MaterialTheme.spacing.huge)
        )

        CategoryComponent(
            items = items,
            onFilterClicked = onFilterClicked,
            shouldDisplayFilter= shouldDisplayFilter,
            Modifier.padding(top = MaterialTheme.spacing.medium)
        )
        if (shouldOpenRecommenderDialog) {
            RecommendedCategoryDetailsDialogComponent(
                item = currentRecommended,
                onDismissRequest = onRecommendedDialogClosed,
            )
        }

        if (shouldOpenFilterDialog) {
            FilterDialogComponent(
                tags = tags,
                onFiltersSelected = onFiltersSelected,
                onDismissRequest = onRecommendedDialogClosed,
                onResetSelected = onResetSelected,
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview
@Composable
fun CategoryScreenPreview() {
    val recommendedItems = listOf(
        RecommendedCategoryDetails("id", "name", ""),
        RecommendedCategoryDetails("id2", "name2", "")
    )
    val items = listOf(
        CategoryItem("id", "name", tags = "#toto #titi"),
        CategoryItem("id2", "name", tags = "#toto #titi"),
        CategoryItem("id3", "name", tags = "#toto #titi"),
        CategoryItem("id4", "name", tags = "#toto #titi"),
        CategoryItem("id5", "name", tags = "#toto #titi"),
    )
    MaterialTheme {
        CategoryScreen(
            title = "Title",
            recommendedItems = recommendedItems,
            items = items,
            tags = listOf(),
            onFiltersSelected = {},
            {},
            currentRecommended = RecommendedCategoryDetailsFake,
            false,
            shouldDisplayFilter = false,
            shouldOpenFilterDialog = false,
            {},
            {},
            {},
            {},
            {},
            Modifier.fillMaxWidth()
        )
    }
}
