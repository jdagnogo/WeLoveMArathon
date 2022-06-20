package com.jdagnogo.welovemarathon.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun FavoritesScreen(
    viewModel: FavViewModel,
    modifier: Modifier = Modifier) {
    val state by viewModel.state.collectAsState()

    FavContent(state = state, modifier = modifier )
}
