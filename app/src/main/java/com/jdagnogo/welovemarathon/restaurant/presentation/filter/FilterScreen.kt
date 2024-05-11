package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantViewModel

@Composable
fun FilterScreen(
    viewModel: RestaurantViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
}