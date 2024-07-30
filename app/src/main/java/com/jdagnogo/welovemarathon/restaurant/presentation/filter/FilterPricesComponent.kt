@file:OptIn(ExperimentalMaterialApi::class)

package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle

@Composable
fun FilterPricesComponent(
    modifier: Modifier = Modifier,
    data: List<String>,
    filterApplied: Set<String>,
    onItemClicked: (Pair<String, Boolean>) -> Unit = {},
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.prices),
        style = SubTitleStyle.copy(fontSize = 18.sp),
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(data.size) { index ->
            val isSelected = filterApplied.contains(data[index])
            val textColor = Color.White.takeIf { isSelected } ?: PrimaryLight
            FilterChip(
                modifier = Modifier.weight(1f),
                border = FilterChipDefaults.filterChipBorder(
                    selected = isSelected,
                    enabled = true,
                    borderColor = Secondary,
                    selectedBorderColor = Color.White,
                ),
                colors = ChipDefaults.filterChipColors(
                    backgroundColor = Color.White,
                    selectedBackgroundColor = Secondary,
                ),
                onClick = { onItemClicked(data[index] to isSelected.not())  },
                selected = isSelected,
            ) {
                Text(
                    text = data[index],
                    color = textColor,
                    textAlign = TextAlign.Center,
                    style = ActivitySubTitleStyle.copy(fontSize = 14.sp),
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}