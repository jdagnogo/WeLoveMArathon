package com.jdagnogo.welovemarathon.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.beach.presentation.beachList
import com.jdagnogo.welovemarathon.common.banner.GifBannerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleIconComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun HomeContent(
    state: HomeState,
    onActivitySelected: (Int) -> Unit,
    onBeachSelected: (String) -> Unit,
    modifier: Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .background(WeLoveMarathonTheme.colors.contentBackground)
            .animateContentSize()) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    HomeTopBarContent(state.hasError, modifier = Modifier)

                    if (state.banner != null) {
                        GifBannerComponent(gifBanner = state.banner)
                    }

                    ActivitiesGridComponent(activities = state.activities,
                        onActivitySelected,
                        modifier = Modifier)

                    TitleIconComponent(title = "Beaches",
                        onClick = { onBeachSelected("aaaaIZX8LAR0YAlSIxjwbjV0") },
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp))
                }
            }
            beachList(state.beaches, onBeachSelected, this)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview(name = "Loading")
@Preview("Dark : Loading", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingComponentPreview() {
    val reducer = HomeReducer()
    val state = reducer.reduce(HomeState(), HomePartialState.LoadingBeaches)
    MaterialTheme {
        HomeContent(state = state,
            modifier = Modifier,
            onActivitySelected = {},
            onBeachSelected = {})
    }
}
