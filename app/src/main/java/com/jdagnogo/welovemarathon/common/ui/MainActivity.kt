package com.jdagnogo.welovemarathon.common.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.common.notifications.PendingNotificationNavigation
import com.jdagnogo.welovemarathon.common.ui.component.NavGraph
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalAnimationApi
@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        PendingNotificationNavigation.setFromExtras(intent.extras)
        setContent {
            MainContent()
        }
    }

    // Needed because MainActivity is launchMode="singleTop": tapping a notification
    // while the app is already running delivers here instead of spawning a new
    // instance (which would otherwise skip onCreate and lose the tap).
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        PendingNotificationNavigation.setFromExtras(intent.extras)
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun MainContent() {
    WeLoveMarathonTheme {
        Surface(color = Primary, modifier = Modifier.fillMaxSize()) {
            NavGraph()
        }
    }
}
