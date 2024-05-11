package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.common.ui.component.DescriptionItemFactory
import com.jdagnogo.welovemarathon.common.ui.component.ExpandableComponent
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.wineDescription

fun LazyListScope.descriptionSection(
    modifier: Modifier = Modifier,
    description : String,
) {
    item("Description") {
        ExpandableComponent(
            modifier = modifier
                .padding(top = MaterialTheme.spacing.medium)
                .padding(horizontal = MaterialTheme.spacing.medium),
        ) { isExpanded ->
            val descriptionItem = DescriptionItemFactory(isExpanded).create()

            Text(
                overflow = descriptionItem.overflow,
                maxLines = descriptionItem.maxLines,
                style = descriptionStyle,
                text = description,
            )
        }
    }
}