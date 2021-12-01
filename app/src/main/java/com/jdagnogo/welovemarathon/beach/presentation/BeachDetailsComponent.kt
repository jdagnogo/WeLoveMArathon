package com.jdagnogo.welovemarathon.beach.presentation

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach
import com.jdagnogo.welovemarathon.beach.domain.toFakeList
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent

@Composable
fun BeachDetailsComponent(
    beach: Beach,
    privateBeaches: List<PrivateBeach> = listOf(),
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .animateContentSize()) {
        val (image, card) = createRefs()
        Image(
            painter = rememberImagePainter(
                data = beach.image,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = beach.name,
            modifier = Modifier
                .padding(top = 8.dp)
                .height(250.dp)
                .constrainAs(image) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.Crop,
        )
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    linkTo(image.bottom, parent.bottom)
                    linkTo(parent.start, parent.end)
                    height = Dimension.fillToConstraints
                }
                .offset(y = -(30.dp))
        ) {
            ConstraintLayout(modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .animateContentSize()) {
                val (
                    name,
                    location,
                    locationIcon, descriptionTitle,
                    privateBeachTitle, privateBeach,
                    description,
                ) = createRefs()
                Text(
                    text = beach.name,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(name) {
                            top.linkTo(parent.top)
                            linkTo(parent.start, parent.end)
                        }
                )
                Image(
                    painter = rememberImagePainter(
                        data = R.drawable.ic_location,
                    ),
                    contentDescription = beach.name,
                    modifier = Modifier
                        .size(32.dp)
                        .constrainAs(locationIcon) {
                            start.linkTo(parent.start)
                            top.linkTo(name.bottom, 24.dp)
                        })
                Text(
                    text = beach.location,
                    modifier = Modifier
                        .constrainAs(location) {
                            top.linkTo(locationIcon.top)
                            bottom.linkTo(locationIcon.bottom)
                            start.linkTo(locationIcon.end, 8.dp)
                        }
                )
                TitleComponent(
                    title = "Description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(descriptionTitle) {
                            top.linkTo(locationIcon.bottom, 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = beach.description,
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(descriptionTitle.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                TitleComponent(
                    title = "Private beaches",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(privateBeachTitle) {
                            top.linkTo(description.bottom, 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                PrivateBeachesComponent(privateBeaches = privateBeaches,
                    modifier = Modifier.constrainAs(privateBeach) {
                        top.linkTo(privateBeachTitle.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(name = "FoodContent")
@Preview("Dark : FoodContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BeachDetailsComponentPreview() {
    MaterialTheme {
        BeachDetailsComponent(Beach().toFakeList().first())
    }
}
