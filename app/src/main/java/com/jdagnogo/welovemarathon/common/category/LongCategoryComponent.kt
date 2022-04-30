package com.jdagnogo.welovemarathon.common.category

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.BeachTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@Composable
fun LongCategoryComponent(
    items: List<LongCategoryItem>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.huge,
            end = MaterialTheme.spacing.huge,
            top = MaterialTheme.spacing.large,
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
        Card(
            elevation = MaterialTheme.spacing.small,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.extraSmall)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = item.image,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = item.name,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }
        Card(
            elevation = MaterialTheme.spacing.small,
            shape = MaterialTheme.shapes.large,
            backgroundColor = PrimaryLight,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.extraSmall)
        ) {
            Text(
                text = item.name,
                style = BeachTitleStyle,
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
        }
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

