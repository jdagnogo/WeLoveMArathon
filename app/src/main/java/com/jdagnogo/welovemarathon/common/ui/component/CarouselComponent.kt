@file:OptIn(ExperimentalPagerApi::class)

package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import kotlinx.coroutines.launch

/**
 * A carousel which opens a full-screen modal window showing the corresponding detailed image when an image is clicked.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)
@Composable
fun CarouselWithPreview(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10, 10, 0, 0),
    urls: List<String>,
    bigImages: List<String>,
    onImageClick: ((index: Int) -> Unit)? = null
) {
    var detailUrl: DetailUrl? by remember { mutableStateOf(null) }
    val pagerState = rememberPagerState(0)
    val scope = rememberCoroutineScope()

    Carousel(modifier, urls, shape = shape, pagerState = pagerState, onImageClick = { index ->
        detailUrl = DetailUrl(bigImages[index], index)
        onImageClick?.invoke(index)
    })

    val url = detailUrl
    if (url != null) {
        val detailPagerState = rememberPagerState(url.index)
        val onDismiss: () -> Unit = {
            detailUrl = null

            scope.launch {
                pagerState.scrollToPage(detailPagerState.currentPage)
            }
        }

        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDismiss
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                CarouselFullScreen(
                    Modifier.fillMaxSize(),
                    bigImages,
                    pagerState = detailPagerState,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    urls: List<String>,
    initialPage: Int = 0,
    shape: Shape,
    pagerState: PagerState = rememberPagerState(initialPage),
    onImageClick: ((index: Int) -> Unit)? = null
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(height = 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(),
            state = pagerState, count = urls.size
        ) { page ->
            Image(
                painter = rememberImagePainter(
                    data = urls[page],
                    builder = {
                        crossfade(true)
                        error(R.drawable.food)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (onImageClick != null) {
                            Modifier.clickable {
                                onImageClick(page)
                            }
                        } else Modifier
                    )
                    .clip(shape = shape),
                contentScale = ContentScale.FillWidth
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
        )
    }
}

@Composable
private fun CarouselFullScreen(
    modifier: Modifier = Modifier,
    urls: List<String>,
    pagerState: PagerState,
    onDismiss: () -> Unit
) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(modifier = modifier, state = pagerState, count = urls.size) { page ->
            Image(
                painter = rememberImagePainter(
                    data = urls[page],
                    builder = {
                        crossfade(true)
                        error(R.drawable.food)
                    }
                ),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_back),
            modifier = Modifier
                .clickable {
                    onDismiss()
                }
                .padding(16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0x55FFFFFF))
                .align(Alignment.TopEnd),
            contentDescription = null)

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(urls.size) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (it == pagerState.currentPage) {
                                Primary
                            } else Color.Gray
                        )
                )
            }
        }
    }
}

data class DetailUrl(val url: String, val index: Int)
