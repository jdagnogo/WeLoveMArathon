package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations.BEACHES_ROUTE

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    HomeContent(
        state = state,
        modifier = modifier,
        onActivitySelected = {
            when (it) {
                0 -> {
                }
            }
        },
        onBeachSelected = {
            navController.navigate(BEACHES_ROUTE)
        })
}
