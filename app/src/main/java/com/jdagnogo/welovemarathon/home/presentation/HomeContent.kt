package com.jdagnogo.welovemarathon.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.blog.domain.Blog
import com.jdagnogo.welovemarathon.blog.domain.fakeList
import com.jdagnogo.welovemarathon.blog.presentation.BlogItem
import com.jdagnogo.welovemarathon.common.banner.GifBannerComponent
import com.jdagnogo.welovemarathon.common.ui.component.LoadingComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.run.domain.Run
import com.jdagnogo.welovemarathon.run.domain.fakeList
import com.jdagnogo.welovemarathon.run.presentation.HighlightCardPadding
import com.jdagnogo.welovemarathon.run.presentation.HighlightCardWidth
import com.jdagnogo.welovemarathon.run.presentation.RunItem

@ExperimentalAnimationApi
@Composable
fun HomeContent(state: HomeState, modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .background(WeLoveMarathonTheme.colors.contentBackground)
            .animateContentSize()) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    HomeTopBarContent(modifier = Modifier)

                    if (state.banner != null) {
                        GifBannerComponent(gifBanner = state.banner)
                    }

                    TitleComponent(
                        title = "Marathon Run",
                        alignRight = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End))

                    MarathonRunList(state,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize())

                    TitleComponent(
                        title = "Blogs",
                        alignRight = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End))
                }
            }
            blogList(state = state, scope = this)
        }
    }
}

fun blogList(state: HomeState, scope: LazyListScope) {
    with(scope) {
        if (state.isLoadingBlogs) {
            item {
                LoadingComponent(modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp), rawId = R.raw.blog)
            }
        } else {
            itemsIndexed(state.blogs) { _, blog ->
                BlogItem(blog = blog)
            }
        }
    }
}

@Composable
fun MarathonRunList(state: HomeState, modifier: Modifier) {
    if (state.isLoadingRuns) {
        LoadingComponent(modifier = Modifier
            .fillMaxWidth()
            .size(200.dp), rawId = R.raw.runing)
    } else {
        val scroll = rememberScrollState(0)

        // The Cards show a gradient which spans 3 cards and scrolls with parallax.
        val gradientWidth = with(LocalDensity.current) {
            (6 * (HighlightCardWidth + HighlightCardPadding).toPx())
        }
        LazyRow(modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp)) {
            itemsIndexed(state.runs) { index, run ->
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
}

@ExperimentalAnimationApi
@Preview(name = "Loading")
@Preview("Dark : Loading", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingComponentPreview() {
    val reducer = HomeReducer()
    val state = reducer.reduce(HomeState(), HomePartialState.LoadingBlogs)
    MaterialTheme {
        HomeContent(state = state, modifier = Modifier)
    }
}

@ExperimentalAnimationApi
@Preview(name = "Blogs")
@Preview("Dark : Blogs", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BlogsComponentPreview() {
    val reducer = HomeReducer()
    val state = reducer.reduce(HomeState(), HomePartialState.OnBlogsSuccess(Blog().fakeList()))
    MaterialTheme {
        HomeContent(state = state, modifier = Modifier)
    }
}

@ExperimentalAnimationApi
@Preview(name = "Runs")
@Preview("Dark : Runs", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RunsComponentPreview() {
    val reducer = HomeReducer()
    val state =
        reducer.reduce(HomeState(), HomePartialState.OnRunsSuccess(Run().fakeList()))
    MaterialTheme {
        HomeContent(state = state, modifier = Modifier)
    }
}

@ExperimentalAnimationApi
@Preview(name = "Full content")
@Composable
fun HomeComponentPreview() {
    val reducer = HomeReducer()
    val state =
        reducer.reduce(HomeState(), HomePartialState.OnRunsSuccess(Run().fakeList()))
    val finalState = reducer.reduce(state, HomePartialState.OnBlogsSuccess(Blog().fakeList()))
    MaterialTheme {
        HomeContent(state = finalState, modifier = Modifier)
    }
}
