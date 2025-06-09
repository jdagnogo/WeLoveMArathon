package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.BeachTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.shadow
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryTitleStyle

@ExperimentalMaterialApi
@Composable
fun LongCategoryComponent(
    items: List<LongCategoryItem>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String,
) {
    Card(
        backgroundColor = Color(0xFF1E4F7B).copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = MaterialTheme.spacing.small),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            if (title != "CULTURE") {
                Text(
                    style = RecommendedCategoryTitleStyle,
                    text = "All",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E4F7B),
                    modifier = Modifier.padding(
                        horizontal = MaterialTheme.spacing.small,
                        vertical = MaterialTheme.spacing.small
                    )
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium,
                    top = 0.dp,
                    bottom = 0.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = modifier.animateContentSize()
            ) {
                items(items.size) { index ->
                    val item = items[index]
                    LongCategoryItemComponent(
                        item = item,
                        onItemSelected = onItemSelected
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun LongCategoryItemComponent(
    item: LongCategoryItem,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .animateContentSize()
        .clickable {
            onItemSelected(item.id)
        }) {
        LongImage(image = item.image, name = item.name)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 0.dp)
        ) {
            Card(
                elevation = 2.dp,
                shape = MaterialTheme.shapes.large,
                backgroundColor = Color(0xFF1E4F7B),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 2.dp,
                        shape = MaterialTheme.shapes.large,
                        clip = false
                    )
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontSize = 17.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.small)
                )
            }
        }
    }
}

@Composable
fun LongImage(
    image: String,
    name: String,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .height(160.dp)
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.small)
    ) {
        Image(
            painter = rememberImagePainter(
                data = image,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = name,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun LongCategoryItemComponentPreview() {
    MaterialTheme {
        LongCategoryItemComponent(
            item = LongCategoryItem(
                "", "name", "location", "image"
            ),
            onItemSelected = {}
        )
    }
}

