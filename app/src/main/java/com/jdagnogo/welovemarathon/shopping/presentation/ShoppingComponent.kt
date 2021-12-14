package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.simpleListComponent
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import com.jdagnogo.welovemarathon.shopping.domain.fakeList

@Composable
fun ShoppingComponent(
    shoppings: List<Shopping>,
    recommended: Shopping? = null,
    currentCategory: ShoppingCategories = ShoppingCategories.Woman,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = currentCategory.name,
                style = MaterialTheme.typography.h5,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            recommended?.let {
                ShoppingRecommendedComponent(recommended, modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.padding(top = 16.dp))
        }

        simpleListComponent(shoppings.map { it.toSimpleListItem() }, this)
    }
}

@Composable
fun ShoppingRecommendedComponent(shopping: Shopping, modifier: Modifier = Modifier) {
    Card(
        elevation = 20.dp,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .width(120.dp)
    ) {
        val context = LocalContext.current
        val uriHandler = LocalUriHandler.current
        ConstraintLayout(modifier = Modifier.background(color = Color.White)) {
            val (iconRef, titleRef, location, number, extraInfoRef, websiteRef) = createRefs()

            Text(text = shopping.name,
                color = Color.Black,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(titleRef) {
                        top.linkTo(iconRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

            Image(
                painter = rememberImagePainter(
                    data = shopping.image,
                    builder = {
                        crossfade(true)
                        error(R.drawable.food)
                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = shopping.name,
                modifier = Modifier
                    .height(100.dp)
                    .constrainAs(iconRef) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    })
            ContactComponent(
                icon = R.drawable.ic_location,
                text = shopping.location,
                iconSize = 24.dp,
                onClicked = { redirectToLink(uriHandler, shopping.locationLink) },
                modifier = Modifier.constrainAs(location) {
                    end.linkTo(parent.end, 8.dp)
                    start.linkTo(parent.start)
                    top.linkTo(extraInfoRef.bottom, 8.dp)
                })

            ContactComponent(
                icon = R.drawable.ic_phone,
                text = shopping.number,
                iconSize = 24.dp,
                onClicked = {
                    redirectToPhone(context, shopping.number)
                },
                modifier = Modifier.constrainAs(number) {
                    end.linkTo(parent.end, 8.dp)
                    start.linkTo(parent.start)
                    top.linkTo(location.bottom, 8.dp)
                })

            ContactComponent(
                icon = R.drawable.ic_phone,
                text = shopping.website,
                iconSize = 24.dp,
                onClicked = { redirectToLink(uriHandler, shopping.website) },
                modifier = Modifier.constrainAs(websiteRef) {
                    end.linkTo(parent.end, 8.dp)
                    start.linkTo(parent.start)
                    top.linkTo(number.bottom, 8.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                })

            Text(text = shopping.description,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .constrainAs(extraInfoRef) {
                        top.linkTo(titleRef.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ShoppingComponentPreview() {
    MaterialTheme {
        val list = Shopping().fakeList()
        ShoppingComponent(shoppings = list, recommended = list.first())
    }
}
