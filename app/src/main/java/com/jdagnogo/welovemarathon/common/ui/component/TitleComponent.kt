package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.drawableID

@Composable
fun TitleComponent(
    iconLeft: Int? = R.drawable.ic_back,
    onLeftIconClicked: () -> Unit = {},
    iconRight: Int? = R.drawable.map,
    onRightIconClicked: () -> Unit = {},
    title: String,
    modifier: Modifier = Modifier
) {
    val iconSize = MaterialTheme.spacing.huge
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.medium)
            .padding(horizontal = MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconLeft != null) {
            Icon(
                painterResource(id = iconLeft),
                contentDescription = "iconLeft",
                tint = Color.Black,
                modifier = modifier
                    .semantics { drawableID = iconLeft }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                    ) {
                        onLeftIconClicked()
                    }
                    .size(iconSize)
                    .background(Color.White, CircleShape)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 26.sp  // Adding fontSize parameter
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        if (iconRight != null) {
            Icon(
                painterResource(id = iconRight),
                contentDescription = "iconRight",
                tint = Color.Black,
                modifier = modifier
                    .semantics { drawableID = iconRight }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                    ) {
                        onRightIconClicked()
                    }
                    .size(iconSize)
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview(name = "Align right", showBackground = true)
@Composable
fun RunTitleRightComponentPreview() {
    MaterialTheme {
        Surface {
            TitleComponent(
                onRightIconClicked = {},
                onLeftIconClicked = {},
                title = "Title",
            )
        }
    }
}
