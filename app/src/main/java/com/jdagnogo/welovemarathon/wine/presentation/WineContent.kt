package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.CarouselWithPreview
import com.jdagnogo.welovemarathon.common.ui.component.DescriptionItemFactory
import com.jdagnogo.welovemarathon.common.ui.component.ExpandableComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.*
import com.jdagnogo.welovemarathon.common.utils.redirectToLink

@Composable
fun WineContent(
    state: WineState,
    onMapSelected: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item("Carousel") {
            Box(modifier = Modifier.fillMaxWidth()) {
                CarouselWithPreview(
                    urls = state.info.images,
                    bigImages = state.info.bigImages,
                    shape = RoundedCornerShape(0, 0, 0, 0),
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
                )
                val uriHandler = LocalUriHandler.current
                TitleComponent(
                    onLeftIconClicked = onBackPressed,
                    onRightIconClicked ={ redirectToLink(uriHandler, state.info.locationLink)},
                    title = state.info.title
                )
            }
        }

        item("Description") {
            ExpandableComponent(
                modifier = modifier
                    .padding(top = MaterialTheme.spacing.medium)
                    .padding(horizontal = MaterialTheme.spacing.medium),
            ) { isExpanded ->
                val descriptionItem = DescriptionItemFactory(isExpanded).create()

                Text(
                    overflow = descriptionItem.overflow,
                    maxLines = descriptionItem.maxLines,
                    style = wineDescription,
                    text = state.info.description,
                )
            }
        }

        item("WineTour") { //rendre clickable
            WineTourComponent(
                items = state.tours,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.medium)
            )
        }

        item("Language") {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Languages :")
                Language(icon = R.drawable.uk)
                Language(icon = R.drawable.greece)
                Language(icon = R.drawable.france)
            }

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.huge)
                    .padding(horizontal = MaterialTheme.spacing.medium),
                style = wineDescription,
                text = state.info.moreInfo
            )


        }

        item("more info") {
            Text(
                text = "Useful information", style = emptyScreenTitle,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.huge)
                    .padding(horizontal = MaterialTheme.spacing.medium),
            )

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.medium)
                    .padding(horizontal = MaterialTheme.spacing.medium),
                style = wineDescription, text = state.info.usefulInfos.replace("\\n", "\n")
            )
        }

        item("our wines") {
            Text(
                text = "Our wines", style = emptyScreenTitle,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.huge)
                    .padding(bottom = MaterialTheme.spacing.medium)
                    .padding(horizontal = MaterialTheme.spacing.medium),
            )

            WineComponent(items = state.info.wines)

            Text( // pas en maj
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.huge)
                    .padding(horizontal = MaterialTheme.spacing.medium),
                textAlign = TextAlign.Center,
                style = wineDescription, text = state.info.wineMoreInfo
            )
        }

        item("Social") {
            SocialMediaComponent(wineSocial = state.socials)
        }
    }
}

@Composable
fun Language(
    modifier: Modifier = Modifier,
    icon: Int,
) {
    Image(
        painter = rememberImagePainter(data = icon),
        contentDescription = null,
        modifier = modifier
            .padding(horizontal = 8.dp)
            .border(width = 1.dp, color = PrimaryLight, shape = CircleShape)
            .size(32.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
fun WineContentPreview() {
    WeLoveMarathonTheme {
        Surface {
            val state = WineState(
                socials = listOf(),
                tours = listOf(),
            )
            WineContent(state)
        }
    }
}