package com.jdagnogo.welovemarathon.common.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.common.ui.component.WLMScaffold
import com.jdagnogo.welovemarathon.common.ui.component.bottombar.BottomBar
import com.jdagnogo.welovemarathon.common.ui.component.wlmNavGraph
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun MenuContent() {
    val appState = rememberAppState()
    WLMScaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomBar(
                    tabs = appState.bottomBarTabs,
                    currentRoute = appState.currentRoute!!,
                    navigateToRoute = appState::navigateToBottomBarRoute,
                    hasNewOffer = true
                )
            }
        },
        scaffoldState = appState.scaffoldState
    ) { innerPaddingModifier ->
        if (!appState.isOnline) {
            OfflineDialog()
        }
        val viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }
        NavHost(
            navController = appState.navController,
            startDestination = MainDestinations.Home.route,
            modifier = Modifier.padding(innerPaddingModifier)
        ) {
            wlmNavGraph(navController = appState.navController, viewModelStoreOwner = viewModelStoreOwner)
        }
    }
}

@Composable
fun OfflineDialog() {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Connection error") },
            text = { Text(text = "Check your internet connection and try again.") },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("Close")
                }
            },
            shape = MaterialTheme.shapes.large
        )
    }
}
