package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.common.ui.component.DescriptionItemFactory
import com.jdagnogo.welovemarathon.common.ui.component.ExpandableComponent
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

private const val MAX_LINES_WHEN_NOT_EXPANDED = 2

fun LazyListScope.descriptionSection(
    modifier: Modifier = Modifier,
    description: String,
) {
    item("Description") {
        var shouldShowExpandButton by remember { mutableStateOf(false) }

        ExpandableComponent(
            modifier = modifier
                .padding(top = 8.dp)
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(bottom = 0.dp),
            shouldShowExpandButton = shouldShowExpandButton,
        ) { isExpanded ->
            val descriptionItem = DescriptionItemFactory(isExpanded).create()

            Text(
                overflow = descriptionItem.overflow,
                maxLines = descriptionItem.maxLines,
                style = descriptionStyle.copy(
                    color = Color.Black,
                    lineHeight = 18.sp
                ),
                text = description,
                onTextLayout = { textLayoutResult ->
                    if (!shouldShowExpandButton) {
                        shouldShowExpandButton = textLayoutResult.lineCount > MAX_LINES_WHEN_NOT_EXPANDED
                    }
                },
            )
        }
    }
}