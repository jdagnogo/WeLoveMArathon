package com.jdagnogo.welovemarathon.map

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.map.domain.MapChip

@Composable
fun MapHeaderComponent(
    mapChips: List<MapChip>,
    screenName: String,
    currentSelected: String,
    onBackPressed: () -> Unit = {},
    onChipClicked: (String) -> Unit = {},
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary).padding(bottom = MaterialTheme.spacing.medium)
        ) {
            TitleComponent(
                title = screenName,
                onLeftIconClicked = onBackPressed,
                iconRight = null
            )
        }
        LazyRow(
            contentPadding = PaddingValues( horizontal = MaterialTheme.spacing.medium),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = modifier
                .fillMaxWidth().padding(top = MaterialTheme.spacing.medium)
        ) {

            itemsIndexed(mapChips) { index, chip ->
                MapChip(
                    isSelected = chip.id == currentSelected,
                    mapChip = chip,
                    onChipClicked = onChipClicked,
                    modifier = modifier,
                )
            }

        }
    }
}

@Composable
fun MapChip(
    isSelected: Boolean,
    mapChip: MapChip,
    onChipClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .clickable { onChipClicked(mapChip.id) }
        .clip(CircleShape)
        .background(Secondary.takeIf { isSelected } ?: Primary)
        .border(width = 1.dp, color = PrimaryLight, shape = CircleShape)
        .padding(vertical = MaterialTheme.spacing.small)
        .padding(horizontal = MaterialTheme.spacing.extraMedium)
    ) {
        Text(
            text = mapChip.name,
            modifier = Modifier
                .align(CenterVertically)
        )
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun MapChipsPreview() {
    val chip = MapChip("id", "", "chip")
    MaterialTheme {
        MapChip(false, chip)
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun MapHeaderComponentPreview() {
    val chips = listOf(
        MapChip(id = "toto1", iconUrl = "", "toto1"),
        MapChip(id = "toto2", iconUrl = "", "toto2"),
        MapChip(id = "toto3", iconUrl = "", "toto3"),
        MapChip(id = "toto4", iconUrl = "", "toto4"),
    )
    MaterialTheme {
        MapHeaderComponent(
            mapChips = chips,
            screenName = "Map title",
            currentSelected = "toto2", modifier = Modifier
        )
    }
}
