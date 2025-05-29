package com.jdagnogo.welovemarathon.culture.presentation

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
fun CulturesBarScreen(
    viewModel: CultureViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    CulturesDetailsContent(
        state = state,
        onLikeClicked = {},
        onMapSelected = { navController.navigate(MainDestinations.Map.createRoute(MapType.Culture.key)) },
        onBackPressed = { navController.popBackStack() },
        modifier = modifier
    )
}
