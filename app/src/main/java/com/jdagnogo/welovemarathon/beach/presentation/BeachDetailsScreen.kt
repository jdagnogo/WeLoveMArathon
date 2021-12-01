package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.toFakeList

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun BeachDetailsScreen(
    viewModel: BeachViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    BeachDetailsContent(state, pagerState = pagerState, scope = scope)
}
