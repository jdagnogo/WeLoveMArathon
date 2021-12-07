package com.jdagnogo.welovemarathon.food.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Background
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food
import com.jdagnogo.welovemarathon.food.domain.restaurant.fakeList

@Composable
fun RestaurantItem(food: Food, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (image, card) = createRefs()
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = modifier
                .width(200.dp)
                .constrainAs(card) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                }
                .offset(y = -(30.dp))
                .clickable {

                }
        ) {
            ConstraintLayout(modifier = Modifier
                .width(width = 100.dp)
                .padding(16.dp)
                .background(Background)) {
                val (name, locationIcon, phoneIcon, location, phone) = createRefs()
                Text(
                    text = food.name,
                    textAlign = TextAlign.Center,
                    color = Primary,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.constrainAs(name) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )

                Image(
                    painter = rememberImagePainter(
                        data = R.drawable.ic_location,
                        builder = {
                            crossfade(true)
                            error(R.drawable.food)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(locationIcon) {
                            start.linkTo(parent.start)
                            top.linkTo(name.bottom, 16.dp)
                        },
                    contentScale = ContentScale.Crop
                )

                Text(text = food.location,
                    maxLines = 2,
                    modifier = Modifier.constrainAs(location) {
                        start.linkTo(locationIcon.end, 16.dp)
                        bottom.linkTo(locationIcon.bottom)
                        end.linkTo(parent.end)
                        top.linkTo(locationIcon.top)
                        width = Dimension.fillToConstraints
                    }
                )

                Image(
                    painter = rememberImagePainter(
                        data = R.drawable.ic_phone,
                        builder = {
                            crossfade(true)
                            error(R.drawable.food)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(phoneIcon) {
                            start.linkTo(parent.start)
                            top.linkTo(locationIcon.bottom, 16.dp)
                        },
                    contentScale = ContentScale.Crop
                )

                Text(text = food.number,
                    maxLines = 2,
                    modifier = Modifier.constrainAs(phone) {
                        start.linkTo(phoneIcon.end, 16.dp)
                        top.linkTo(phoneIcon.top)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )
            }
        }

        Image(
            painter = rememberImagePainter(
                data = food.image,
                builder = {
                    crossfade(true)
                    error(R.drawable.food)
                }
            ),
            contentDescription = food.name,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, PrimaryDark, CircleShape)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun RestaurantItemPreview() {
    MaterialTheme {
        RestaurantItem(Food().fakeList().first())
    }
}
