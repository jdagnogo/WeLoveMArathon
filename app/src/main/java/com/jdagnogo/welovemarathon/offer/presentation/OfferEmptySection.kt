package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.runtime.Composable
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant

@Composable
fun OfferEmptySection(
    offer: OfferWithRestaurant,
    onGetOfferClicked: () -> Unit = {}
) {
    // Directly call OfferContent instead of rendering an empty section
    OfferContent(
        offer = offer,
        onGetOfferClicked = onGetOfferClicked
    )
}