package com.jdagnogo.welovemarathon.common.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.jdagnogo.welovemarathon.common.ui.component.BottomBar
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.common.ui.component.WLMScaffold
import com.jdagnogo.welovemarathon.common.ui.component.wlmNavGraph
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun MainContent() {
    WeLoveMarathonTheme {
        ProvideWindowInsets {
            val appState = rememberAppState()
            WLMScaffold(
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        BottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::navigateToBottomBarRoute
                        )
                    }
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = MainDestinations.HOME_ROUTE,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    wlmNavGraph()
                }
            }
        }
    }
}
