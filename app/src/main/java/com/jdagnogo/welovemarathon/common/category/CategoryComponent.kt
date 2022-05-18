package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryItemTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.tagsTitleStyle
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CategoryComponent(
    items: List<CategoryItem>,
    onFilterClicked: (isVisible: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FilterComponent(
            onFilterClicked = onFilterClicked,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.huge
            )
        )

        CategoryGridComponent(
            items = items,
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
        modifier = modifier
    ) {
        Column(Modifier.padding(MaterialTheme.spacing.small)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = RecommendedCategoryItemTitleStyle,
                    text = item.name
                )
                val uriHandler = LocalUriHandler.current
                val context = LocalContext.current
                ContactComponent(
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                    icon = R.drawable.location,
                    iconSize = 24.dp,
                    onClicked = { redirectToLink(uriHandler, item.locationLink) },
                )
                ContactComponent(
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                    icon = R.drawable.ic_phone,
                    iconSize = 24.dp,
                    onClicked = {
                        redirectToPhone(context, item.number)
                    },
                )
            }
            Divider(color = Color.White, thickness = 1.dp)
            Text(
                overflow = TextOverflow.Ellipsis,
                style = tagsTitleStyle,
                text = item.tags,
                maxLines = 1,
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

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CategoryGridComponent(
    items: List<CategoryItem>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.huge,
            end = MaterialTheme.spacing.huge,
            top = MaterialTheme.spacing.large,
            bottom = MaterialTheme.spacing.large,
        ),
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
    onFilterClicked: (isVisible: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = R.drawable.filter,
        ),
        contentDescription = "Filter menu",
        modifier = modifier
            .fillMaxWidth()
            .clickable { onFilterClicked(true) },
        contentScale = ContentScale.FillWidth
    )
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


