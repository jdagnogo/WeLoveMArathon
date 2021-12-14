package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.domain.RightMenuData
import com.jdagnogo.welovemarathon.common.domain.toFakeList
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary

@Composable
fun RightMenuComponent(
    currentSelected: Int = 0,
    categories: List<RightMenuData> = listOf(),
    onCategoryClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .background(Secondary)
            .width(100.dp)) {

        itemsIndexed(categories) { index, category ->
            RightMenuItem(isSelected = index == currentSelected,
                rightMenuData = category,
                onCategoryClicked)
        }
    }
}

@Composable
fun RightMenuItem(
    isSelected: Boolean = false,
    rightMenuData: RightMenuData,
    onCategoryClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier
        .clickable {
            onCategoryClicked(rightMenuData.name)
        }) {
        val (image, title, divider) = createRefs()
        Image(
            painter = rememberImagePainter(
                data = rightMenuData.getIcon(),
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = rightMenuData.name,
            modifier = Modifier
                .size(80.dp.takeIf { isSelected } ?: 60.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(4.dp.takeIf { isSelected } ?: 2.dp, PrimaryDark, CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    linkTo(parent.start, parent.end)
                },
            contentScale = ContentScale.Crop
        )
        Text(
            text = rightMenuData.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1.takeIf { isSelected }
                ?: MaterialTheme.typography.caption,
            modifier = Modifier
                .constrainAs(title) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(image.bottom, 8.dp)
                    width = Dimension.fillToConstraints
                }
        )
        DividerComponent(modifier = Modifier.constrainAs(divider) {
            top.linkTo(title.bottom, 8.dp)
            linkTo(parent.start, parent.end)
        })
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun RightMenuComponentComponentPreview() {
    MaterialTheme {
        RightMenuComponent(2, RightMenuData().toFakeList())
    }
}
