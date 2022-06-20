package com.jdagnogo.welovemarathon.common.like

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun LikeComponent(
    id: String = "",
    isFavItem: Boolean = false,
    onLikeClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Crossfade(
        isFavItem,
        animationSpec = tween(1000)
    ) { targetState ->
        Icon(
            painterResource(if (targetState) R.drawable.fav else R.drawable.ic_fav_unselected),
            contentDescription = "icon",
            modifier = modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onLikeClicked(id)
            }
        )
    }
}

@Preview("Normal")
@Composable
fun LikeComponentPreview() {
    WeLoveMarathonTheme {
        Surface {
            LikeComponent(isFavItem = false)
        }
    }
}

@Preview("Selected")
@Composable
fun LikeComponentSelectedPreview() {
    WeLoveMarathonTheme {
        Surface {
            LikeComponent(isFavItem = true)
        }
    }
}
