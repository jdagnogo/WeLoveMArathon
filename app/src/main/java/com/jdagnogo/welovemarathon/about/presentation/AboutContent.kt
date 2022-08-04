@file:OptIn(ExperimentalFoundationApi::class)

package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.utils.redirectToLink

@Composable
fun AboutContent(
    state: AboutState,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    LazyColumn(
        modifier = modifier
    ) {
        item {
            AboutHeaderComponent(
                mail = state.mail,
                phone = state.phone,
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
            AboutTitle(title = "Our team", modifier = Modifier.padding(vertical = 16.dp))
            FlowRow(
                mainAxisSpacing = 30.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
                crossAxisSpacing = 8.dp,
                lastLineMainAxisAlignment = FlowMainAxisAlignment.Center,
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                repeat(state.members.size) { index ->
                    TeamComponent(state.members[index])
                }
            }
        }

        item {
            Text(
                text = "Privacy policy",
                textDecoration = Underline,
                color = Secondary,
                textAlign = TextAlign.Center, modifier = Modifier.padding(24.dp)
                    .fillMaxWidth()
                    .clickable {
                        redirectToLink(uriHandler = uriHandler, state.policy)
                    }
            )
        }

        item {
            AboutTitle(title = "Some photos", modifier = Modifier.padding(vertical = 16.dp))
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