@file:OptIn(ExperimentalMaterialApi::class)

package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.*
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.wine.domain.WineTour

@Composable
fun WineTourComponent(
    items: List<WineTour>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.huge,
        ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier.animateContentSize()
    ) {
        items(items.size) { index ->
            val item = items[index]
            WineTourContentComponent(item = item)
        }
    }
}

@Composable
fun WineTourContentComponent(
    item: WineTour,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier.animateContentSize()
    ) {
        val (image, card) = createRefs()
        val uriHandler = LocalUriHandler.current
        Card(
            backgroundColor = PrimaryLight,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .constrainAs(card) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top, 80.dp)
                }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 80.dp)
                    .padding(horizontal = 16.dp)
                    .width(250.dp),
            )
            {
                Text(
                    text = item.name,
                    textAlign = TextAlign.Center,
                    style = emptyScreenTitle,
                    color = SecondaryLight,
                    modifier = modifier.fillMaxWidth()

                )

                Text(
                    text = item.description,
                    textAlign = TextAlign.Center,
                    style = wineDescription,
                    modifier = modifier
                        .padding(top = 16.dp)
                        .height(130.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(modifier = Modifier.weight(1.5f)) {
                        HorizontalWineTourInfo(
                            icon = R.drawable.ic_clock,
                            text = item.hour,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        HorizontalWineTourInfo(
                            icon = R.drawable.ic_people,
                            text = item.pack,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .clickable {
                                redirectToLink(uriHandler, item.link)
                            }
                            .border(
                                width = 1.dp,
                                color = Secondary
                            )
                            .padding(vertical = 16.dp)
                            .padding(horizontal = 24.dp),
                        text = "Book",
                        style = contactStyle,
                        color = Secondary,
                    )
                }
            }
        }

        Image(
            painter = rememberImagePainter(data = item.icon),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .constrainAs(image) {
                    top.linkTo(card.top)
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(card.top)
                },
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun HorizontalWineTourInfo(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Icon(
            painterResource(icon),
            contentDescription = "icon",
            tint = TagColor,
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = wineDescription,
            modifier = Modifier.padding(start = 16.dp)

        )
    }
}

@Preview
@Composable
fun WineTourComponentContentPreview() {
    WeLoveMarathonTheme {
        Surface {
            val wineTour = WineTour(
                name = "Full visite",
                icon = "",
                description = "Description",
                images = listOf(),
                hour = "1 hour",
                ordinal = 0,
                id = ""
            )
            WineTourContentComponent(wineTour)
        }
    }
}