package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.map.domain.MapType
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalPagerApi
@InternalCoroutinesApi
@Composable
fun BeachesBarScreen(
    viewModel: BeachViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    BeachesBarContent(
        state = state,
        onMapSelected = { navController.navigate(MainDestinations.Map.createRoute(MapType.Beach.key)) },
        onBackPressed = { navController.popBackStack() },
        onFilterClicked = {},
        modifier = modifier
    )
}