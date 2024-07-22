package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.restaurant.domain.IconNameFilter

@Composable
fun FilterRowIconComponent(
    modifier: Modifier = Modifier,
    title: String,
    data: List<IconNameFilter>,
    filterApplied: Set<String>,
    onItemClicked: (Pair<String, Boolean>) -> Unit = {},
) {
    Text(
        modifier = modifier,
        text = title,
        style = SubTitleStyle.copy(fontSize = 18.sp),
    )
    Spacer(modifier = Modifier.padding(8.dp))

    LazyRow(
        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier
    ) {
        items(data.size) { index ->
            val isSelected = filterApplied.contains(data[index].name)
            TypeOfItem(
                icon = data[index].icon,
                name = data[index].name,
                isSelected = isSelected,
                onItemClicked = { onItemClicked(data[index].name to isSelected.not()) }
            )
        }
    }
}