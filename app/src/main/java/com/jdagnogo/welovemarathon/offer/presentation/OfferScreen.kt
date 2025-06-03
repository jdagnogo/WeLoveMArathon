package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant

@Composable
fun OfferScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OfferViewModel,
) {
    val state by viewModel.state.collectAsState()
    val offer = state.offer

    if (offer == null) {
        OfferEmptySection()
    } else {
        OfferContent(
            offer = offer,
            onGetOfferClicked = {
                viewModel.dispatchEvent(OfferViewModel.OfferUiEvent.OnBookNow(offer.toString()))
            }
        )
    }
}