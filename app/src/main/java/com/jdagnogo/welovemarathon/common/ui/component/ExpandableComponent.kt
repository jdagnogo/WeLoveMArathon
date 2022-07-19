package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle

@Composable
internal fun ExpandableComponent(
    labelMore: String = "See more",
    labelLess: String = "See less",
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(isExpanded: Boolean) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val text = if (isExpanded) labelLess else labelMore
    Column(
        modifier = modifier.animateContentSize()
    ) {
        Column(
            content = { content(isExpanded) }, modifier = Modifier.padding(vertical = 8.dp)
        )

        MoreLessComponent(
            text = text,
            isExpanded = isExpanded,
            modifier = Modifier.align(alignment = CenterHorizontally),
            onClick = {
                isExpanded = isExpanded.not()
            }
        )
    }
}

@Composable
internal fun MoreLessComponent(
    text: String,
    isExpanded: Boolean,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick() },

        ) {
        Text(
            text = text,
            color = Secondary,
            style = SubTitleStyle
        )
        Icon(
            if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            null,
            modifier = Modifier
                .size(16.dp)
                .padding(start = 4.dp),
            tint = Secondary,
        )
    }
}
