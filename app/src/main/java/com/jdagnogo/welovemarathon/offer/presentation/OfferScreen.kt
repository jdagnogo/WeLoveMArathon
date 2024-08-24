package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.material.Text
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
        Text(text = " NO OFFER")
    } else {
        OfferDetailsContent(modifier = modifier, state = state)
    }
}