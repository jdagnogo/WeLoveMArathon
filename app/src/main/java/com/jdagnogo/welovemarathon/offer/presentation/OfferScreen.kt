package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun OfferScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OfferViewModel,
) {
    val state by viewModel.state.collectAsState()
    if (state.offer == null) {
        OfferEmptySection()
    } else {
        OfferDetailsContent(
            modifier = modifier,
            state = state,
            onOfferActivationClicked = {
                viewModel.dispatchEvent(OfferViewModel.OfferUiEvent.OnBookNow(it))
            })
    }
}