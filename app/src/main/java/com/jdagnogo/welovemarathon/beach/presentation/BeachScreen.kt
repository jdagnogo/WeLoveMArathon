package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.map.domain.MapType
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun BeachScreen(
    viewModel: BeachViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    BeachContent(
        onRecommendedDialogClosed = {

        },
        state = state,
        onBeachSelected = {
            viewModel.dispatchEvent(BeachUiEvent.OnBeachSelected(it))
            navController.navigate(MainDestinations.BeachesBar.route)
        },
        onRecommendedSelected = {
           // open dialog
        },
        onMapSelected = {
            navController.navigate(MainDestinations.Map.createRoute(MapType.Beach.key))
        },
        onBackPressed = {
            navController.popBackStack()
        },
        modifier = modifier
    )
}
