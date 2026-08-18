package com.jdagnogo.welovemarathon.events.presentation

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.theme.ContentBackgroundLight
import com.jdagnogo.welovemarathon.common.ui.theme.EventsColor
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.events.domain.Event
import com.jdagnogo.welovemarathon.events.domain.EventCategory

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EventsContent(
    state: EventsState,
    listState: LazyListState = rememberLazyListState(),
    onSearchQueryChanged: (String) -> Unit = {},
    onCategorySelected: (String) -> Unit = {},
    onAllCategoriesSelected: () -> Unit = {},
    onEventClicked: (Event) -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ContentBackgroundLight)
            .statusBarsPadding(),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Events",
                style = TitleStyle.copy(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.medium),
            )
            IconButton(
                onClick = onSettingsClicked,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Notification settings",
                    tint = Color.Black,
                )
            }
        }

        SearchField(
            query = state.searchQuery,
            onQueryChanged = onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small,
                ),
        )

        LazyRow(
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.extraSmall,
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            item {
                val isSelected = state.selectedCategory == null
                FilterChip(
                    selected = isSelected,
                    onClick = onAllCategoriesSelected,
                    border = if (isSelected) null else BorderStroke(1.dp, Color.Black),
                    colors = ChipDefaults.filterChipColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black,
                        selectedBackgroundColor = Color.Black,
                        selectedContentColor = Color.White,
                    ),
                ) {
                    Text(text = "All", color = if (isSelected) Color.White else Color.Black)
                }
            }
            items(EventCategory.entries) { category ->
                val isSelected = state.selectedCategory == category.label
                FilterChip(
                    selected = isSelected,
                    onClick = { onCategorySelected(category.label) },
                    // Unselected: just an outline in the category's color, so the
                    // color reads as a preview without competing with the selected
                    // state. Selected: the same color fills in solid.
                    border = if (isSelected) null else BorderStroke(1.dp, category.color),
                    colors = ChipDefaults.filterChipColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black,
                        selectedBackgroundColor = category.color,
                        selectedContentColor = Color.White,
                    ),
                ) {
                    Text(
                        text = "${category.emoji} ${category.label}",
                        color = if (isSelected) Color.White else Color.Black,
                    )
                }
            }
        }

        if (state.groupedEvents.isEmpty()) {
            EventsEmptyState()
        } else {
            // Scrolling to "Today" on arrival is driven by EventsScreen's
            // ON_RESUME handler (via the shared listState passed in), not
            // from here - see EventsScreen.kt for why.
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.medium,
                ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                state.groupedEvents.forEach { (day, dayEvents) ->
                    item(key = "header_$day") {
                        DayHeader(label = day.toDisplayLabel(), count = dayEvents.size)
                    }
                    items(dayEvents, key = { "${day}_${it.id}" }) { event ->
                        EventListItem(event = event, day = day, onClick = { onEventClicked(event) })
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchField(query: String, onQueryChanged: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text("Search events...", style = descriptionStyle) },
        singleLine = true,
        shape = RoundedCornerShape(50),
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = Color.Gray)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear search", tint = Color.Gray)
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = EventsColor,
            unfocusedBorderColor = Color.Transparent,
        ),
        modifier = modifier,
    )
}

@Composable
private fun DayHeader(label: String, count: Int) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.small, bottom = MaterialTheme.spacing.extraSmall),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.h3.copy(color = Color.Black),
        )
        Text(
            text = if (count == 1) " · 1 event" else " · $count events",
            style = descriptionStyle,
            modifier = Modifier.padding(bottom = 2.dp),
        )
    }
}

@Composable
private fun EventsEmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(MaterialTheme.spacing.huge),
        ) {
            Icon(
                imageVector = Icons.Filled.EventBusy,
                contentDescription = null,
                tint = EventsColor.copy(alpha = 0.5f),
                modifier = Modifier.size(64.dp),
            )
            Text(
                text = "No upcoming events",
                style = emptyScreenTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.medium),
            )
            Text(
                text = "Check back soon for new events in Marathon!",
                style = emptyScreenSubTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview(name = "EventsContent")
@Preview("Dark : EventsContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EventsContentPreview() {
    MaterialTheme {
        EventsContent(state = EventsState())
    }
}
