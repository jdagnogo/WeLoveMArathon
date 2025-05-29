package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CategoryComponent(
    item: CategoryItem,
    onLikeClicked: (String) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        backgroundColor = Color.White,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp),
        ) {
            val backgroundRes = getCategoryBackgroundRes(title)
            if (backgroundRes != null) {
                Image(
                    painter = painterResource(id = backgroundRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(3.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        color = Color(0xFF1E4F7B).copy(alpha = 0.8f),
                        blendMode = BlendMode.Color
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.name,
                        color = Color.White,
                        fontSize = 20.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.ExtraBold,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 16f
                            )
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopStart)
                            .fillMaxWidth(0.75f)
                    )
                    
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 2.dp)
                            .width(IntrinsicSize.Min)
                    ) {
                        ContactComponent(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .border(1.dp, Color(0xFF1E4F7B), CircleShape),
                            icon = R.drawable.location,
                            iconSize = 24.dp,
                            tint = Color(0xFF1E4F7B),
                            backgroundColor = Color.White,
                            onClicked = { redirectToLink(uriHandler, item.locationLink) }
                        )
                        ContactComponent(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 48.dp)
                                .border(1.dp, Color(0xFF1E4F7B), CircleShape),
                            icon = R.drawable.ic_phone,
                            iconSize = 24.dp,
                            tint = Color(0xFF1E4F7B),
                            backgroundColor = Color.White,
                            onClicked = { redirectToPhone(context, item.number) }
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CategoryGridComponent(
    items: List<CategoryItem>,
    onLikeClicked: (String) -> Unit,
    title: String,
    shouldDisplayFilter: Boolean,
    onFilterClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.extraLarge,
                bottom = 0.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = RecommendedCategoryTitleStyle,
            text = "All",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        if (shouldDisplayFilter) {
            FilterComponent(
                shouldDisplayFilter = shouldDisplayFilter,
                onFilterClicked = onFilterClicked,
                modifier = Modifier
            )
        }
    }
    LazyColumn(
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.medium,
            top = 0.dp,
            bottom = MaterialTheme.spacing.small,
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier.animateContentSize()
    ) {
        items(items.size) { index ->
            CategoryComponent(
                item = items[index],
                onLikeClicked = onLikeClicked,
                title = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun FilterComponent(
    shouldDisplayFilter: Boolean,
    onFilterClicked: (isVisible: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (shouldDisplayFilter) {
        val interactionSource = remember { MutableInteractionSource() }

        Image(
            painter = rememberImagePainter(
                data = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/icons%2FNew%20Food%2Ffilter.png?alt=media&token=8319c192-f27c-4975-9aae-4eaf7e9d6807",
            ),
            contentDescription = "Filter menu",
            modifier = modifier
                .size(33.dp)
                .clickable(interactionSource = interactionSource, indication = null) {
                    onFilterClicked(true)
                },
            colorFilter = ColorFilter.tint(Color(0xFF1E4F7B)),
            contentScale = ContentScale.Fit
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun FilterComponentPreview() {
    MaterialTheme {
        Surface {
            FilterComponent(
                shouldDisplayFilter = true,
                {})
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun CategoryComponentPreview() {
    val items = listOf(
        CategoryItem(
            id = "id",
            name = "name",
            locationLink = "",
            number = "",
            isFavItem = false
        )
    )
    MaterialTheme {
        CategoryComponent(
            item = items[0],
            onLikeClicked = {} ,
            title = "WATER SPORTS"
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun CategoryItemComponentPreview() {
    val item = CategoryItem("id", "name", tags = "#toto #titi")
    MaterialTheme {
        CategoryComponent(
            item = item,
            onLikeClicked = {},
            title = "WATER SPORTS"
        )
    }
}

private fun getCategoryBackgroundRes(title: String): Int? = when (title.uppercase()) {
    "WATER SPORTS" -> R.drawable.watersports
    "HIKING" -> R.drawable.hiking
    "HORSE RIDING" -> R.drawable.horseriding
    "TENNIS" -> R.drawable.tennis
    "RUNNING" -> R.drawable.running
    "OTHER" -> R.drawable.other
    "WOMAN", "MAN", "KID" -> R.drawable.clothing
    "PET" -> R.drawable.pet
    "LOCAL" -> R.drawable.local
    "MARKET" -> R.drawable.market
    else -> null
}
