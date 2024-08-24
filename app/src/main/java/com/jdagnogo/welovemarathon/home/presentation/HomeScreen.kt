@file:OptIn(ExperimentalMaterial3Api::class)

package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.offer.presentation.OfferContent

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val shouldOpenOfferBottomSheet = rememberSaveable(state) { mutableStateOf(state.shouldOpenOfferBottomSheet) }
    HomeContent(
        state = state,
        modifier = modifier,
        onActivitySelected = {
            when (it) {
                0 -> {
                    navController.navigate(MainDestinations.ActivitiesSubMenu.route)
                }

                1 -> {
                    navController.navigate(MainDestinations.Beaches.route)
                }

                2 -> {
                    navController.navigate(MainDestinations.Cultures.route)
                }

                3 -> {
                    navController.navigate(MainDestinations.ShoppingSubMenu.route)
                }

                4 -> {
                    navController.navigate(MainDestinations.Restaurant.route)
                }

                5 -> {
                    navController.navigate(MainDestinations.Wine.route)
                }
            }
        },
    )
    if (shouldOpenOfferBottomSheet.value) {
        MaterialTheme {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(fraction = 0.9f),
                containerColor = Color.White,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                onDismissRequest = {
                    shouldOpenOfferBottomSheet.value = false
                }
            ) {
                OfferContent()
            }
        }
    }
}
