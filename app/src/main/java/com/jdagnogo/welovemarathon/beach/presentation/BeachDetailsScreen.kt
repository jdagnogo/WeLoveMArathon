package com.jdagnogo.welovemarathon.beach.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun BeachDetailsScreen(
    viewModel: BeachViewModel,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    viewModel.dispatchEvent(BeachUiEvent.ScrollTo(currentPage))
    BeachDetailsContent(state,
        pagerState = viewModel.pagerState,
        scope = scope,
        onBeachSelected = {
            viewModel.dispatchEvent(BeachUiEvent.FetchPrivatesBeaches(it))
            viewModel.shouldRedirect = false
        },
        modifier = modifier)
}
