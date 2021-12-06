package com.jdagnogo.welovemarathon.beach.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.*
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach
import com.jdagnogo.welovemarathon.beach.domain.toFakeList
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun BeachDetailsContent(
    state: BeachState,
    currentSelectedId: String = "",
    pagerState: PagerState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    val beaches = state.beaches
    if (beaches.isEmpty()) {
        Text(text = "Loading ...")
    } else {
        Surface(modifier = modifier
            .fillMaxSize()
            .background(WeLoveMarathonTheme.colors.contentBackground)) {
            Column(modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .background(WeLoveMarathonTheme.colors.contentBackground)) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Secondary,
                    contentColor = PrimaryDark,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    }
                ) {
                    // Add tabs for all of our pages
                    beaches.forEachIndexed { index, beach ->
                        Tab(
                            text = { Text(beach.name, color = Color.Black) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        )
                    }
                }
                HorizontalPager(
                    count = beaches.size,
                    state = pagerState,
                ) { index ->
                    BeachDetailsComponent(beach = beaches[index], PrivateBeach().toFakeList())
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(name = "FoodContent")
@Preview("Dark : FoodContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BeachDetailsContentPreview() {
    MaterialTheme {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        BeachDetailsContent(pagerState = pagerState,
            scope = scope,
            state = BeachState(beaches = Beach().toFakeList()))
    }
}
