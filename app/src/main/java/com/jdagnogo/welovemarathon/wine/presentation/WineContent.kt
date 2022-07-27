package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.wineDescription
import com.jdagnogo.welovemarathon.wine.domain.WineSocial

@Composable
fun WineContent(
    state: WineState,
    onMapSelected: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                TitleComponent(
                    onLeftIconClicked = onBackPressed,
                    onRightIconClicked = onMapSelected,
                    title = ""
                )

                Image(
                    painter = rememberImagePainter(
                        data = R.drawable.ic_wlm_logo,
                        builder = {
                            crossfade(true)
                            error(R.drawable.ic_wlm_logo)
                        }
                    ),
                    contentDescription = "Logo",
                    modifier = Modifier.align(Alignment.Center)
                        .padding(top = MaterialTheme.spacing.huge)
                        .height(250.dp),
                )
            }
        }

        item {
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                style = wineDescription,
                text = "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchan"
            )
        }

        item {
            WineTourComponent(wineTours = state.tours)
        }

        item {
            Text(
                modifier = Modifier,
                style = wineDescription,
                text = "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchan"
            )
        }

        item {
            SocialMediaComponent(wineSocial = state.socials)
        }
    }
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