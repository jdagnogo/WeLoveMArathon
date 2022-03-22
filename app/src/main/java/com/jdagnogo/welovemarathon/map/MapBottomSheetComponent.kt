package com.jdagnogo.welovemarathon.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.map.domain.MapItem

@Composable
fun MapBottomSheetComponent(
    mapItems: List<MapItem>,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(500.dp)
            .padding(MaterialTheme.spacing.medium)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(mapItems) { _, mapItem ->
                Column {
                    Text(
                        text = mapItem.name,
                    )
                    DividerComponent(color = Primary)
                }
            }
        }
    }
}
