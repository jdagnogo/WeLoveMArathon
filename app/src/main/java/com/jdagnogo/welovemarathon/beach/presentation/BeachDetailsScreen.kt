package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun BeachDetailsScreen(
    viewModel: BeachViewModel,
    beachId: String?,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    if (state.privateBeaches.isEmpty()) {
        viewModel.dispatchEvent(BeachUiEvent.FetchPrivatesBeaches(beachId ?: ""))
    }
    BeachDetailsContent(state,
        currentSelectedId = beachId ?: "",
        pagerState = pagerState,
        scope = scope,
        onBeachSelected = { viewModel.dispatchEvent(BeachUiEvent.FetchPrivatesBeaches(it)) },
        modifier = modifier)
}

