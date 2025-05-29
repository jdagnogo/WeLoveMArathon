package com.jdagnogo.welovemarathon.culture.presentation

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
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingUiEvent
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun CultureScreen(
    viewModel: CultureViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    CultureContent(
        onRecommendedDialogClosed = {
            viewModel.dispatchEvent(CultureUiEvent.OnRecommendedDialogClosed)
        },
        state = state,
        onCultureSelected = {
            viewModel.dispatchEvent(CultureUiEvent.OnCultureSelected(it))
            navController.navigate(MainDestinations.CulturesDetails.route)
        },
        onRecommendedSelected = {
            viewModel.dispatchEvent(CultureUiEvent.OnRecommendedItemSelected(it))
        },
        onMapSelected = {
           // navController.navigate(MainDestinations.Map.createRoute(MapType.Culture.key))
        },
        onBackPressed = {
            navController.popBackStack()
        },
        modifier = modifier
    )
}
