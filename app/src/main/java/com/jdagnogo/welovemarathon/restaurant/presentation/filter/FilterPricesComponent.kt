@file:OptIn(ExperimentalMaterialApi::class)

package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle

@Composable
fun FilterPricesComponent(
    modifier: Modifier = Modifier,
    data: List<String>,
    filterApplied: Set<String>,
    onItemClicked: (Pair<String, Boolean>) -> Unit = {},
) {

    val primaryBlue = Color(0xFF1E4F7B)
    val lightBlue = Color(0xFF3B7AB5)

    Text(
        modifier = modifier,
        text = stringResource(id = R.string.prices),
        style = SubTitleStyle.copy(fontSize = 18.sp, color = Color.Black),
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(data.size) { index ->
            val isSelected = filterApplied.contains(data[index])
            FilterChip(
                modifier = Modifier.weight(1f),
                border = FilterChipDefaults.filterChipBorder(
                    selected = isSelected,
                    enabled = true,
                    borderColor = primaryBlue,
                    selectedBorderColor = lightBlue,
                ),
                colors = ChipDefaults.filterChipColors(
                    backgroundColor = Color.White,
                    selectedBackgroundColor = lightBlue,
                ),
                onClick = { onItemClicked(data[index] to isSelected.not()) },
                selected = isSelected,
            ) {
                Text(
                    text = data[index],
                    color = if (isSelected) Color.White else Color.Black,
                    textAlign = TextAlign.Center,
                    style = ActivitySubTitleStyle.copy(fontSize = 14.sp),
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}