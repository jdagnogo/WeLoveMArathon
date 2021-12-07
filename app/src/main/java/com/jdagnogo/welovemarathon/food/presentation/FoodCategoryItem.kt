package com.jdagnogo.welovemarathon.food.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Neutral4
import com.jdagnogo.welovemarathon.food.domain.FoodCategory

@Composable
fun FoodCategoryItem(
    foodCategory: FoodCategory,
    onCategorySelected: (FoodCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 20.dp,
        shape = MaterialTheme.shapes.large,
        backgroundColor = foodCategory.color,
        modifier = modifier
            .size(100.dp)
            .clickable {
                onCategorySelected(foodCategory)
            }
    ) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (image, title) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = foodCategory.icon,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = foodCategory.title,
                modifier = Modifier
                    .constrainAs(image) {
                        linkTo(parent.start, parent.end)
                        linkTo(parent.top, parent.bottom)
                    }
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = foodCategory.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .background(Neutral4)
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun BlogItemPreview() {
    MaterialTheme {
        FoodCategoryItem(FoodCategory.RESTAURANT, {})
    }
}
