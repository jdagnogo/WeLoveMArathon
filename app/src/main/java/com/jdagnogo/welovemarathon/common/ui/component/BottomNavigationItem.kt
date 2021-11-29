package com.jdagnogo.welovemarathon.common.ui.component

import android.content.res.Configuration
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun BottomNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelected),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .layoutId("icon")
                .scale(if (selected) 1.5f else 1f)
                .padding(horizontal = TextIconSpacing),
            content = icon
        )
    }
}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomNavigationItemPreview() {
    val springSpec = SpringSpec<Float>(
        stiffness = 800f,
        dampingRatio = 0.8f
    )
    WeLoveMarathonTheme {
        Surface {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                        contentDescription = null
                    )
                },
                selected = true,
                {}, springSpec
            )
        }
    }
}

val TextIconSpacing = 2.dp
val BottomNavHeight = 56.dp
val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
val BottomNavigationItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
