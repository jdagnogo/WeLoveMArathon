package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.CategoryGridTagStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryItemTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CategoryComponent(
    items: List<CategoryItem>,
    onFilterClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FilterComponent(
            onFilterClicked = onFilterClicked,
        )

        CategoryGridComponent(
            items = items,
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun CategoryItemComponent(
    item: CategoryItem,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        backgroundColor = PrimaryLight,
    ) {
        Column(modifier.padding(MaterialTheme.spacing.small)) {
            Row() {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = RecommendedCategoryItemTitleStyle,
                    text = item.name
                )
            }
            Divider(color = Color.White, thickness = 1.dp)
            Text(
                overflow = TextOverflow.Ellipsis,
                style = CategoryGridTagStyle,
                text = "#toto #titi #tutu",
                maxLines = 1,
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CategoryGridComponent(
    items: List<CategoryItem>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.huge,
            end = MaterialTheme.spacing.huge,
            top = MaterialTheme.spacing.large,
        ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier.animateContentSize()
    ) {
        items(items.size) { index ->
            val item = items[index]
            CategoryItemComponent(
                item = item,
            )
        }
    }
}

@Composable
fun FilterComponent(
    onFilterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Divider(
            color = Color.White, thickness = 1.dp,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.huge)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(150.dp)
                .clickable { onFilterClicked() }
                .background(Secondary)
                .padding(
                    vertical = MaterialTheme.spacing.small
                )
        ) {
            Text(
                style = CategoryGridTagStyle,
                text = "Filter"
            )
            Icon(
                painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                tint = Primary,
                modifier = modifier
                    .padding(start = MaterialTheme.spacing.extraSmall)
                    .size(MaterialTheme.spacing.medium)
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun FilterComponentPreview() {
    MaterialTheme {
        FilterComponent({})
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun CategoryComponentPreview() {
    val items = listOf(
        CategoryItem("id", "name", tags = "#toto #titi"),
        CategoryItem("id2", "name", tags = "#toto #titi"),
        CategoryItem("id3", "name", tags = "#toto #titi"),
        CategoryItem("id4", "name", tags = "#toto #titi"),
        CategoryItem("id5", "name", tags = "#toto #titi"),
    )
    MaterialTheme {
        CategoryComponent(
            items = items, onFilterClicked = {})
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun CategoryItemComponentPreview() {
    val item = CategoryItem("id", "name", tags = "#toto #titi")
    MaterialTheme {
        CategoryItemComponent(
            item = item
        )
    }
}


