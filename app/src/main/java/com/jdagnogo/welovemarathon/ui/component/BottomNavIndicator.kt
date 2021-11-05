package com.jdagnogo.welovemarathon.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.home.BottomNavIndicatorShape
import com.jdagnogo.welovemarathon.home.BottomNavigationItemPadding
import com.jdagnogo.welovemarathon.ui.theme.WeLoveMarathonTheme

@Composable
fun BottomNavIndicator(
    strokeWidth: Dp = 2.dp,
    color: Color = MaterialTheme.colors.primary,
    shape: Shape = BottomNavIndicatorShape,
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(BottomNavigationItemPadding)
            .border(strokeWidth, color, shape)
    )
}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomNavIndicatorPreview() {
    WeLoveMarathonTheme {
        Surface {
            BottomNavIndicator()
        }
    }
}
