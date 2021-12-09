package com.jdagnogo.welovemarathon.run.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.common.ui.theme.offsetGradientBackground
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.run.domain.Run
import com.jdagnogo.welovemarathon.run.domain.fakeList

val HighlightCardWidth = 170.dp
val HighlightCardPadding = 16.dp

@Composable
fun RunItem(
    run: Run,
    index: Int, scroll: Int, gradient: List<Color>,
    gradientWidth: Float, modifier: Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val left = index * with(LocalDensity.current) {
        (HighlightCardWidth + HighlightCardPadding).toPx()
    }
    Card(
        modifier = modifier
            .size(
                width = 170.dp,
                height = 250.dp
            )
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { redirectToLink(uriHandler, run.link) })
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) {
                val gradientOffset = left - (scroll / 3f)
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .offsetGradientBackground(gradient, gradientWidth, gradientOffset)
                )
                Image(
                    painter = rememberImagePainter(
                        data = run.image,
                        builder = {
                            crossfade(true)
                            error(R.drawable.ic_wlm_logo)
                        }
                    ),
                    contentDescription = run.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = run.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = run.date,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun RunComponentList(runs: List<Run>, modifier: Modifier) {
    val scroll = rememberScrollState(0)

    // The Cards show a gradient which spans 3 cards and scrolls with parallax.
    val gradientWidth = with(LocalDensity.current) {
        (6 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }
    LazyRow(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)) {
        itemsIndexed(runs) { index, run ->
            val gradient = when ((index / 2) % 2) {
                0 -> WeLoveMarathonTheme.colors.gradient
                else -> WeLoveMarathonTheme.colors.gradientVariant
            }
            RunItem(run = run,
                index = index,
                gradient = gradient,
                gradientWidth = gradientWidth,
                scroll = scroll.value,
                modifier = Modifier)
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun RunItemPreview() {
    MaterialTheme {
        RunItem(Run().fakeList().first(),
            index = 0,
            gradient = WeLoveMarathonTheme.colors.gradient,
            gradientWidth = gradientWidth,
            scroll = 0,
            modifier = Modifier)
    }
}

private val gradientWidth
    @Composable
    get() = with(LocalDensity.current) {
        (3 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }
