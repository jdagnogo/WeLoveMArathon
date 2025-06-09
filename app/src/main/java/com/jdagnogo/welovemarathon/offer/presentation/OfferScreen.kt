package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant

@Composable
fun OfferScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OfferViewModel,
) {
    val state by viewModel.state.collectAsState()
    val currentOffer = state.offer

    if (currentOffer == null) {
        OfferEmptySection()
    } else {
        OfferContent(
            offer = currentOffer,
            onGetOfferClicked = {
                viewModel.dispatchEvent(OfferViewModel.OfferUiEvent.OnBookNow(currentOffer.toString()))
            }
        )
    }
}

@Composable
private fun EmptyOffersState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No offers are available right now",
            style = MaterialTheme.typography.h5.copy(
                color = Color(0xFF1E4F7B),
                fontSize = 24.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}