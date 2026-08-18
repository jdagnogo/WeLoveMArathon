package com.jdagnogo.welovemarathon.events.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun NotificationSettingsScreen(
    viewModel: NotificationSettingsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Turning a toggle on is also a natural moment to ask for the OS
    // notification permission (Android 13+) if it hasn't been granted yet -
    // whatever the user decides in that system dialog, the app-level
    // preference and topic subscription below still reflect their intent.
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { /* no-op - see comment above */ }

    fun requestPermissionIfNeeded() {
        if (EventNotificationPreferences.shouldRequestNotificationPermission(context)) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    NotificationSettingsContent(
        state = state,
        onNewEventsToggled = { enabled ->
            if (enabled) requestPermissionIfNeeded()
            viewModel.dispatchEvent(NotificationSettingsUiEvent.OnNewEventsToggled(enabled))
        },
        onCancellationsToggled = { enabled ->
            if (enabled) requestPermissionIfNeeded()
            viewModel.dispatchEvent(NotificationSettingsUiEvent.OnCancellationsToggled(enabled))
        },
        onBackClicked = { navController.popBackStack() },
        modifier = modifier,
    )
}
