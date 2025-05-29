@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package com.jdagnogo.welovemarathon.favorites.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.jdagnogo.welovemarathon.common.category.CategoryComponent
import com.jdagnogo.welovemarathon.common.like.domain.Favorite
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.clearAllStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink

@Composable
fun FavContent(
    state: FavViewModel.FavState,
    onRedirectToHomeClicked: () -> Unit = {},
    onClearAllClicked: () -> Unit = {},
    modifier: Modifier,
) {
    if (state.hasFavorites) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = rememberImagePainter(
                    data = R.drawable.bg_favorites,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = "Favorite header",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Card(
                elevation = 10.dp,
                shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp),
                backgroundColor = Primary,
                modifier = Modifier
                    .padding(top = 200.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()

            ) {
                Column {
                    Text(
                        text = "Clear all",
                        style = clearAllStyle,
                        modifier =
                        Modifier
                            .align(alignment = Alignment.End)
                            .padding(top = MaterialTheme.spacing.medium)
                            .padding(horizontal = MaterialTheme.spacing.semiHuge)
                            .clickable {
                                onClearAllClicked()
                            }
                    )
                    Spacer(modifier = Modifier)
                    FavItemsComponent(items = state.favorites)
                }

            }
        }
    } else {
        EmptyFavContent(
            onRedirectToHomeClicked = onRedirectToHomeClicked,
            modifier
        )
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun FavContentPreview() {
    MaterialTheme {
        Surface {
            val state = FavViewModel.FavState(
                favorites = listOf(
                    Favorite("id1", "toto"),
                    Favorite("id1", "titi"),
                )
            )
            FavContent(state = state, modifier = Modifier)
        }
    }
}
