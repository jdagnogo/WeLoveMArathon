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

    // Ensure you have an offer to pass
    val offer = state.offer ?: OfferWithRestaurant(
        id = "default",
        title = "Default Offer",
        description = "This is a default offer description.",
        promoCode = "DEFAULT",
        validUntil = "N/A",
        restaurantName = "Default Restaurant",
        discount = "0%",
        restaurantId = "0",
        restaurantImage = ""
    )

    OfferContent(
        offer = offer,
        onGetOfferClicked = {
            viewModel.dispatchEvent(OfferViewModel.OfferUiEvent.OnBookNow(offer.toString()))
        }
    )
}