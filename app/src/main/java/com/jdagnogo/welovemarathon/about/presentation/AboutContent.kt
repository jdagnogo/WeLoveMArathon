package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AboutContent(
    state: AboutState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            AboutHeaderComponent(
                socialMedias = state.socialMedias,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }

        item {
            AboutDescription()
        }

        item {
            TeamComponent(state.members)
        }

        item {
            AboutTitle(title = "Some photos", modifier = Modifier.padding(bottom = 16.dp))
        }

        items(count = state.photos.size) { index ->
            PhotosComponent(photo = state.photos[index])
        }

    }
}


@Preview
@Composable
fun AboutContentPreview() {
    AboutContent(
        state = AboutState(

        )
    )
}