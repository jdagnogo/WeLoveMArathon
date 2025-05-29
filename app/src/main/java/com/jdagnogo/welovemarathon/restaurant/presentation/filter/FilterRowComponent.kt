@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)

package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun FilterRowComponent(
    modifier: Modifier = Modifier,
    title: String,
    data: List<String>,
    filterApplied: Set<String>,
    onItemClicked: (Pair<String, Boolean>) -> Unit = {},
) {

    val primaryBlue = Color(0xFF1E4F7B)
    val lightBlue = Color(0xFF3B7AB5)

    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp, color = Color.Black)
    )

    Spacer(modifier = Modifier.padding(4.dp))

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        data.forEach { item ->
            val isSelected = filterApplied.contains(item)
            FilterChip(
                onClick = { onItemClicked(item to !isSelected) },
                selected = isSelected,
                border = BorderStroke(1.dp, primaryBlue),
                colors = ChipDefaults.filterChipColors(
                    backgroundColor = if (isSelected) lightBlue else Color.White,
                    contentColor = if (isSelected) Color.White else primaryBlue
                )
            ) {
                Text(
                    text = item,
                    style = ActivitySubTitleStyle.copy(
                        fontSize = 12.sp,
                        color = if (isSelected) Color.White else Color.Black
                    )
                )
            }
        }
    }
}