package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle

@Composable
fun FilterPricesComponent(
    modifier: Modifier = Modifier,
    data: List<String>,
    filterApplied: Set<String>,
    onItemClicked: (Pair<String, Boolean>) -> Unit = {},
) {
    Column(modifier) {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.drinks),
            style = SubTitleStyle.copy(fontSize = 18.sp),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        repeat(data.size) { index ->
            val isSelected = filterApplied.contains(data[index])
            Row {
                Checkbox(modifier = Modifier.padding(end = 16.dp),
                    checked = isSelected,
                    onCheckedChange = {
                        onItemClicked(data[index] to isSelected.not())
                    })

                Text(text = data[index])
            }

        }

    }
}