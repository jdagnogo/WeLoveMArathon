package com.jdagnogo.welovemarathon.common.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun DividerComponent(
    modifier: Modifier = Modifier,
    color: Color = WeLoveMarathonTheme.colors.divider,
    thickness: Dp = 1.dp,
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}

@Preview("default", showBackground = true)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DividerPreview() {
    WeLoveMarathonTheme {
        Box(Modifier.size(height = 10.dp, width = 100.dp)) {
            DividerComponent(Modifier.align(Alignment.Center))
        }
    }
}
