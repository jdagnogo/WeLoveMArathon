package com.jdagnogo.welovemarathon.common.type_two

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.CategoryItemComponent
import com.jdagnogo.welovemarathon.common.category.CategoryTag
import com.jdagnogo.welovemarathon.common.category.FilterComponent
import com.jdagnogo.welovemarathon.common.category.FilterDialogComponent
import com.jdagnogo.welovemarathon.common.category.LongImage
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryItemTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TypeTwoComponent(
    title: String,
    item: TypeTwoItem,
    categoryItems: List<CategoryItem>,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onFilterClicked: (isVisible: Boolean) -> Unit,
    shouldDisplayFilter: Boolean,
    shouldOpenFilterDialog: Boolean,
    tags: List<CategoryTag>,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    onDismissFilterRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Primary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            TitleComponent(
                title = title,
                onLeftIconClicked = onBackPressed,
                onRightIconClicked = onMapSelected
            )
        }
        item {
            LongImage(
                item.image, item.name, modifier = Modifier
                    .padding(top = MaterialTheme.spacing.huge)
                    .padding(horizontal = MaterialTheme.spacing.huge)
            )
        }
        item {
            TypeTwoItemPresentation(
                item = item, modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.huge,
                    )
                    .padding(top = MaterialTheme.spacing.medium)
            )
        }
        item {
            Column {
                FilterComponent(
                    shouldDisplayFilter = shouldDisplayFilter,
                    onFilterClicked = onFilterClicked,
                    modifier = Modifier
                        .height(MaterialTheme.spacing.extraHuge)
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.huge
                        )
                )
            }

        }


        items(categoryItems.size) { index ->
            val categoryItem = categoryItems[index]
            CategoryItemComponent(
                item = categoryItem,
                modifier = Modifier.padding(
                    start = MaterialTheme.spacing.huge,
                    end = MaterialTheme.spacing.huge,
                    top = MaterialTheme.spacing.medium,
                    bottom = MaterialTheme.spacing.medium,
                )
            )

        }
    }

    if (shouldOpenFilterDialog) {
        FilterDialogComponent(
            tags = tags,
            onFiltersSelected = onFiltersSelected,
            onDismissRequest = onDismissFilterRequest,
            onResetSelected = onResetSelected,
        )
    }
}

@Composable
fun TypeTwoItemPresentation(item: TypeTwoItem, modifier: Modifier = Modifier) {
    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        backgroundColor = PrimaryLight,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = RecommendedCategoryItemTitleStyle,
                    text = item.name
                )
                val uriHandler = LocalUriHandler.current
                ContactComponent(
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                    icon = R.drawable.location,
                    iconSize = 24.dp,
                    onClicked = { redirectToLink(uriHandler, item.locationLink) },
                )
            }

            Divider(color = Color.White, thickness = 1.dp)

            Text(
                style = ActivitySubTitleStyle,
                textAlign = TextAlign.Start,
                text = item.description,
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.medium
                    )
                    .padding(
                        horizontal = MaterialTheme.spacing.small
                    )
            )
        }
    }
}

@Preview
@Composable
fun TypeTwoItemPresentationPreview() {
    MaterialTheme {
        TypeTwoItemPresentation(
            item = TypeTwoItem(
                name = "name",
                location = "location",
                locationLink = "locationLink",
                description = "looooong description",
                image = "image"
            ),
        )
    }
}

@Preview
@Composable
fun TypeTwoComponentPreview() {
    val items = listOf(
        CategoryItem("id", "name", tags = "#toto #titi"),
        CategoryItem("id2", "name", tags = "#toto #titi"),
        CategoryItem("id3", "name", tags = "#toto #titi"),
        CategoryItem("id4", "name", tags = "#toto #titi"),
        CategoryItem("id5", "name", tags = "#toto #titi"),
    )
    MaterialTheme {
        TypeTwoComponent(
            title = "Title",
            item = TypeTwoItem(
                name = "name",
                location = "location",
                locationLink = "locationLink",
                description = "looooong description",
                image = "image"
            ),
            categoryItems = items,
            onBackPressed = {},
            onMapSelected = {},
            onFilterClicked = {},
            onDismissFilterRequest = {},
            shouldDisplayFilter = true,
            shouldOpenFilterDialog = false,
            onResetSelected = {},
            onFiltersSelected = {},
            tags = emptyList(),
        )
    }
}