package com.jdagnogo.welovemarathon.beach.presentation

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
    beachId: String?,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    if (state.privateBeaches.isEmpty()) {
        viewModel.dispatchEvent(BeachUiEvent.FetchPrivatesBeaches(beachId ?: ""))
    }
    BeachDetailsContent(state,
        pagerState = viewModel.pagerState,
        scope = scope,
        onBeachSelected = {
            viewModel.dispatchEvent(BeachUiEvent.FetchPrivatesBeaches(it))
        },
        modifier = modifier)
}
