package com.jdagnogo.welovemarathon.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.jdagnogo.welovemarathon.common.ui.component.LoadingComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.home.domain.Blog
import com.jdagnogo.welovemarathon.home.domain.fakeList

@ExperimentalAnimationApi
@Composable
fun HomeContent(state: HomeState, modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .background(WeLoveMarathonTheme.colors.contentBackground)) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    HomeTopBarContent(modifier = Modifier)

                    TitleComponent(
                        title = "Marathon Run",
                        alignRight = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End))

                    MarathonRunList(state,
                        modifier = Modifier.fillMaxWidth())

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
        when (state) {
            is HomeState.OnBlogsSuccess -> {
                itemsIndexed(state.data) { _, blog ->
                    BlogItem(blog = blog)
                }
            }
            is HomeState.LoadingBlogs -> {
                item {
                    LoadingComponent(modifier = Modifier)
                }
            }
            else -> {
            }
        }
    }
}

@Composable
fun MarathonRunList(state: HomeState, modifier: Modifier) {
    when (state) {
        is HomeState.LoadingRuns -> {
            LoadingComponent(modifier = Modifier)
        }

        is HomeState.OnRunsSuccess -> {
            val scroll = rememberScrollState(0)

            // The Cards show a gradient which spans 3 cards and scrolls with parallax.
            val gradientWidth = with(LocalDensity.current) {
                (6 * (HighlightCardWidth + HighlightCardPadding).toPx())
            }
            LazyRow(modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp)) {
                itemsIndexed(state.data) { index, run ->
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
        else -> {
        }
    }
}

@ExperimentalAnimationApi
@Preview(name = "Full content")
@Preview("Dark : Full content", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingComponentPreview() {
    val fakeData = Blog().fakeList()
    MaterialTheme {
        HomeContent(state = HomeState.OnBlogsSuccess(fakeData), modifier = Modifier)
    }
}
