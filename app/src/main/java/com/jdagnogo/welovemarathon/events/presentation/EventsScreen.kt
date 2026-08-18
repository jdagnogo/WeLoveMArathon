package com.jdagnogo.welovemarathon.events.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EventsScreen(
    viewModel: EventsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Notification preferences default to "on" (see EventNotificationPreferences),
    // but that default is meaningless until something actually calls
    // subscribeToTopic at least once - a SharedPreferences default has no
    // side effect on its own. Asking here, on first real visit to Events,
    // is also what US3.4 originally called for (not burying the ask inside
    // a settings screen most people will never open).
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { /* no-op - subscription state below reflects the stored preference regardless */ }

    // Runs every time this destination actually becomes the visible/resumed
    // screen - not just the first time it's ever composed. A bare
    // LaunchedEffect(Unit) isn't reliable enough here: bottom-nav tabs use
    // saveState/restoreState, which can keep this composable's state alive in
    // the background instead of tearing it down on every tab switch, so
    // ON_RESUME is the correct "the user actually arrived here again" signal.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.dispatchEvent(EventsUiEvent.OnScreenEntered)

                if (EventNotificationPreferences.shouldRequestNotificationPermission(context)) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                coroutineScope.launch {
                    EventNotificationPreferences.syncSubscriptions(context)
                }

                // The list stays in plain chronological order (past -> today ->
                // future) - this just positions the initial scroll so today/next
                // event is the first thing visible, with older history reachable
                // by scrolling up, like a normal calendar.
                coroutineScope.launch {
                    val groupedEvents = snapshotFlow { state.groupedEvents }.first { it.isNotEmpty() }
                    listState.scrollToItem(groupedEvents.indexOfTodayHeader())
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    EventsContent(
        state = state,
        listState = listState,
        onSearchQueryChanged = { viewModel.dispatchEvent(EventsUiEvent.OnSearchQueryChanged(it)) },
        onCategorySelected = { viewModel.dispatchEvent(EventsUiEvent.OnCategorySelected(it)) },
        onAllCategoriesSelected = { viewModel.dispatchEvent(EventsUiEvent.OnAllCategoriesSelected) },
        onEventClicked = { navController.navigate(MainDestinations.EventDetail.createRoute(it.id)) },
        onSettingsClicked = { navController.navigate(MainDestinations.EventNotificationSettings.route) },
        modifier = modifier,
    )
}
