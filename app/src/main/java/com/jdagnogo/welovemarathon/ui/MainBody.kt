package com.jdagnogo.welovemarathon.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.jdagnogo.welovemarathon.ui.component.BottomBar
import com.jdagnogo.welovemarathon.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.ui.component.WLMScaffold
import com.jdagnogo.welovemarathon.ui.component.wlmNavGraph
import com.jdagnogo.welovemarathon.ui.theme.WeLoveMarathonTheme

@Composable
fun MainBody() {
    WeLoveMarathonTheme {
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
