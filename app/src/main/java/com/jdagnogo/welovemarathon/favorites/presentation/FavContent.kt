package com.jdagnogo.welovemarathon.favorites.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.*

@Composable
fun FavContent(
    state: FavViewModel.FavState,
    onRedirectToHomeClicked: () -> Unit = {},
    modifier: Modifier,
) {
    if (state.hasFavorites) {

    } else {
        EmptyFavContent(
            onRedirectToHomeClicked = onRedirectToHomeClicked,
            modifier
        )
    }
}

@Composable
fun EmptyFavContent(
    onRedirectToHomeClicked: () -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = MaterialTheme.spacing.medium),

        ) {
        Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.huge))

        Image(
            painter = rememberImagePainter(
                data = R.drawable.ic_fav_unselected,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = "",
            modifier = Modifier.size(300.dp),
        )

        Text(
            text = "Nothing added as favorites yet", style = emptyScreenTitle,
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        )

        Text(
            text = "You can add them from the each category of the home page",
            style = emptyScreenSubTitle,
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        )

        Button(
            shape = MaterialTheme.shapes.medium,
            onClick = { onRedirectToHomeClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Secondary),
            modifier = modifier
                .padding(top = MaterialTheme.spacing.huge)
        ) {
            Text(text = "Go to categories")
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun EmptyFavContentPreview() {
    MaterialTheme {
        Surface {
            EmptyFavContent({},modifier = Modifier.fillMaxSize(),)
        }
    }
}
