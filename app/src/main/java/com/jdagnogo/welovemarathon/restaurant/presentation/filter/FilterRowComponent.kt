@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)

package com.jdagnogo.welovemarathon.restaurant.presentation.filter

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
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun FilterRowComponent(
    modifier: Modifier = Modifier,
    title: String,
    data: List<String>,
) {
    Text(
        modifier = modifier.padding(horizontal = 16.dp),
        text = title,
        style = SubTitleStyle.copy(fontSize = 18.sp),
    )

    Spacer(modifier = Modifier.padding(4.dp))

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        repeat(data.size) { index ->
            var selected by remember { mutableStateOf(false) }
            val textColor = Color.White.takeIf { selected } ?: PrimaryLight
            FilterChip(
                modifier = Modifier,
                border = FilterChipDefaults.filterChipBorder(
                    selected = selected,
                    enabled = true,
                    borderColor = Secondary,
                    selectedBorderColor = Color.White,
                ),
                colors = ChipDefaults.filterChipColors(
                    backgroundColor = Color.White,
                    selectedBackgroundColor = Secondary,
                ),
                onClick = { selected = !selected },
                selected = selected,
            ) {
                Text(
                    text = data[index],
                    color = textColor,
                    style = ActivitySubTitleStyle.copy(fontSize = 12.sp),
                    modifier = Modifier
                )
            }
        }
    }
}