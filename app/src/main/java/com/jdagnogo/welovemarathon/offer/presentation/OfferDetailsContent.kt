package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OfferDetailsContent(
    modifier: Modifier,
    state: OfferViewModel.OfferState,
) {
    Text(text = state.offer.restaurant?.name ?: "fails")
}