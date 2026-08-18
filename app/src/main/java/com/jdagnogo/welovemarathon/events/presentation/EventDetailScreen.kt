package com.jdagnogo.welovemarathon.events.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun EventDetailScreen(
    viewModel: EventDetailViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val event by viewModel.event.collectAsState()

    // Briefly null right after navigating in (Room hasn't emitted yet) or if
    // the event somehow no longer exists locally - nothing meaningful to
    // show either way, so just don't render content rather than crash.
    event?.let {
        EventDetailContent(
            item = it,
            onCloseClicked = { navController.popBackStack() },
            modifier = modifier,
        )
    }
}
