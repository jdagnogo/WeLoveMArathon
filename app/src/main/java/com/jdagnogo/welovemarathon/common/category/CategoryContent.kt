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
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun CategoryScreen(
    title: String,
    recommendedItems: List<RecommendedCategoryItem>,
    items: List<CategoryItem>,
    onItemSelected: (String) -> Unit,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onFilterClicked: () -> Unit,
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
            onLeftIconClicked = onMapSelected,
            onRightIconClicked = onBackPressed
        )

        RecommendedCategoryComponent(
            recommendedItems = recommendedItems,
            onItemSelected = onItemSelected,
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        )

        CategoryComponent(
            items = items,
            onFilterClicked = onFilterClicked,
            Modifier.padding(top = MaterialTheme.spacing.medium)
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview
@Composable
fun CategoryScreenPreview() {
    val recommendedItems = listOf(
        RecommendedCategoryItem("id", "name", ""),
        RecommendedCategoryItem("id2", "name2", "")
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
            {},
            {},
            {},
            {},
            Modifier.fillMaxWidth()
        )
    }
}
