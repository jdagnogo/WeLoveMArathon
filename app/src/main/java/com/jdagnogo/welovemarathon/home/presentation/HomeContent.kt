package com.jdagnogo.welovemarathon.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.banner.GifBannerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.run.presentation.RunContent
import com.jdagnogo.welovemarathon.run.presentation.RunPartialState
import com.jdagnogo.welovemarathon.run.presentation.RunReducer
import com.jdagnogo.welovemarathon.run.presentation.RunState

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
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(name = "Loading")
@Preview("Dark : Loading", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingComponentPreview() {
    val reducer = RunReducer()
    val state = reducer.reduce(RunState(), RunPartialState.Loading)
    MaterialTheme {
        RunContent(state = state, modifier = Modifier)
    }
}
