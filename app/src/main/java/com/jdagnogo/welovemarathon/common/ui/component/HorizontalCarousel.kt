package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jdagnogo.welovemarathon.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalCarousel(
    item: List<HorizontalCarouselItem>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(height = 300.dp)
            .fillMaxWidth()
    ) {
        val pagerState = rememberPagerState()
        HorizontalPager(
            count = item.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            PagerSampleItem(
                item = item[page],
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
        )
    }
}

/**
 * Simple pager item which displays an image
 */
@Composable
internal fun PagerSampleItem(
    item: HorizontalCarouselItem,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = rememberImagePainter(
            data = item.url,
            builder = {
                crossfade(true)
                error(R.drawable.food)
            }
        ),
        contentDescription = item.name,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10, 10, 0, 0)),
        contentScale = ContentScale.FillWidth
    )

}


data class HorizontalCarouselItem(
    val url: String,
    val name: String,
)