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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun LongCategoryScreen(
    title: String,
    recommendedItems: List<RecommendedCategoryDetails>,
    items: List<LongCategoryItem>,
    currentRecommended: RecommendedCategoryDetails?,
    shouldOpenRecommenderDialog: Boolean,
    onRecommendedSelected: (String) -> Unit,
    onItemSelected: (String) -> Unit,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onRecommendedDialogClosed: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        TitleComponent(
            title = title,
            onLeftIconClicked = onBackPressed,
            onRightIconClicked = onMapSelected
        )

        RecommendedCategoryComponent(
            recommendedItems = recommendedItems,
            onRecommendedSelected = onRecommendedSelected,
            modifier = Modifier.padding(top = MaterialTheme.spacing.small)
        )

        LongCategoryComponent(
            items = items,
            onItemSelected = onItemSelected,
            Modifier.padding(top = MaterialTheme.spacing.medium),
            title = title,
        )

        if (shouldOpenRecommenderDialog) {
            RecommendedCategoryDetailsDialogComponent(
                item = currentRecommended,
                onDismissRequest = onRecommendedDialogClosed,
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview
@Composable
fun LongCategoryContentPreview() {
    val recommendedItems = listOf(
        RecommendedCategoryDetails("id", "name", ""),
        RecommendedCategoryDetails("id2", "name2", "")
    )
    val items = listOf(
        LongCategoryItem("id", "name"),
        LongCategoryItem("id2", "name"),
        LongCategoryItem("id3", "name"),
        LongCategoryItem("id4", "name"),
        LongCategoryItem("id5", "name"),
    )
    MaterialTheme {
        LongCategoryScreen(
            title = "Title",
            recommendedItems = recommendedItems,
            items = items,
            currentRecommended = RecommendedCategoryDetailsFake,
            false,
            {},
            {},
            {},
            {},
            {},
            Modifier.fillMaxWidth(),
        )
    }
}
