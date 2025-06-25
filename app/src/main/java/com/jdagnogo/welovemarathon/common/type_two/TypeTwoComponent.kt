package com.jdagnogo.welovemarathon.common.type_two

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.*
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.component.HtmlTextComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.*
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TypeTwoComponent(
    title: String,
    item: TypeTwoItem,
    categoryItems: List<CategoryItem>,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onLikeClicked: (String) -> Unit,
    onFilterClicked: (isVisible: Boolean) -> Unit,
    shouldDisplayFilter: Boolean,
    shouldOpenFilterDialog: Boolean,
    tags: List<CategoryTag>,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    onDismissFilterRequest: () -> Unit = {},
    itemType: ItemType = ItemType.ItemPresentation,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = MaterialTheme.spacing.small),
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
                    .padding(top = 0.dp)
                    .padding(horizontal = MaterialTheme.spacing.small)
            )
        }
        item {
            when (itemType) {
                ItemType.Description -> {
                    TypeTwoItemDescription(
                        item = item, modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.small)
                            .padding(top = MaterialTheme.spacing.medium)
                    )
                }
                else -> {
                    TypeTwoItemPresentation(
                        item = item, modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.huge)
                            .padding(top = MaterialTheme.spacing.medium)
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.medium
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (shouldDisplayFilter){
                    Text(
                        text = "Organized Beach Bars",
                        style = RecommendedCategoryTitleStyle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    FilterComponent(
                        shouldDisplayFilter = shouldDisplayFilter,
                        onFilterClicked = onFilterClicked,
                        modifier = Modifier
                    )
                }
            }
        }

        items(categoryItems.size) { index ->
            val categoryItem = categoryItems[index]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.huge,
                        top = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.medium,
                    )
            ) {
                CategoryComponent(
                    item = categoryItem,
                    onLikeClicked = onLikeClicked,
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                )
            }
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
fun TypeTwoItemDescription(item: TypeTwoItem, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(bottom = 32.dp)
    ) {
        val uriHandler = LocalUriHandler.current
        val context = LocalContext.current
        val (card, phone, website, location) = createRefs()
        val spacing = MaterialTheme.spacing.medium

        Card(
            elevation = MaterialTheme.spacing.small,
            shape = MaterialTheme.shapes.large,
            backgroundColor = Color.White,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.small)
                .constrainAs(card) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top)
                }
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (description, phoneBtn, websiteBtn, locationBtn) = createRefs()

                HtmlTextComponent(
                    text = item.description,
                    modifier = Modifier
                        .constrainAs(description) {
                            linkTo(parent.start, parent.end, endMargin = 72.dp)
                            top.linkTo(parent.top)
                            width = Dimension.percent(0.75f)
                        }
                        .padding(
                            top = MaterialTheme.spacing.medium,
                            bottom = MaterialTheme.spacing.large,
                            start = MaterialTheme.spacing.small,
                            end = MaterialTheme.spacing.small
                        )
                )

                ContactComponent(
                    modifier = Modifier
                        .constrainAs(phoneBtn) {
                            top.linkTo(parent.top, margin = spacing)
                            end.linkTo(parent.end, margin = spacing)
                        }
                        .size(48.dp)
                        .border(1.dp, Color(0xFF1E4F7B), CircleShape),
                    icon = R.drawable.ic_phone,
                    iconSize = 24.dp,
                    backgroundColor = Color.White,
                    tint = Color(0xFF1E4F7B),
                    onClicked = { redirectToPhone(context, item.phone) },
                )

                ContactComponent(
                    modifier = Modifier
                        .constrainAs(websiteBtn) {
                            top.linkTo(phoneBtn.bottom, margin = spacing)
                            end.linkTo(parent.end, margin = spacing)
                        }
                        .size(48.dp)
                        .border(1.dp, Color(0xFF1E4F7B), CircleShape),
                    icon = R.drawable.ic_link,
                    backgroundColor = Color.White,
                    iconSize = 24.dp,
                    tint = Color(0xFF1E4F7B),
                    onClicked = { redirectToLink(uriHandler, item.website) },
                )

                ContactComponent(
                    modifier = Modifier
                        .constrainAs(locationBtn) {
                            top.linkTo(websiteBtn.bottom, margin = spacing)
                            end.linkTo(parent.end, margin = spacing)
                        }
                        .size(48.dp)
                        .border(1.dp, Color(0xFF1E4F7B), CircleShape),
                    icon = R.drawable.location,
                    iconSize = 24.dp,
                    tint = Color(0xFF1E4F7B),
                    backgroundColor = Color.White,
                    onClicked = { redirectToLink(uriHandler, item.locationLink) }
                )
            }
        }
    }
}

@Composable
fun TypeTwoItemPresentation(item: TypeTwoItem, modifier: Modifier = Modifier) {
    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        backgroundColor = Color.White,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.h6.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    text = item.name
                )
                val uriHandler = LocalUriHandler.current
                ContactComponent(
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                    icon = R.drawable.location,
                    iconSize = 24.dp,
                    tint = Color(0xFF1E4F7B),
                    backgroundColor = Color.White,
                    onClicked = { redirectToLink(uriHandler, item.locationLink) },
                )
            }

            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            Text(
                style = MaterialTheme.typography.body1.copy(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Start,
                text = item.description,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.medium)
                    .padding(horizontal = MaterialTheme.spacing.small)
            )
        }
    }
}

sealed class ItemType {
    object Description : ItemType()
    object ItemPresentation : ItemType()
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
fun TypeTwoItemDescriptionPreview() {
    MaterialTheme {
        TypeTwoItemDescription(
            item = TypeTwoItem(
                name = "name",
                location = "location",
                locationLink = "locationLink",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with ",
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
            onLikeClicked = {},
            tags = emptyList(),
        )
    }
}