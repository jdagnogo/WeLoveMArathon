package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jdagnogo.welovemarathon.food.presentation.FoodViewModel

@ExperimentalFoundationApi
@Composable
fun BeachDetailsScreen(
    viewModel: BeachViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    BeachDetailsContent()
}
