package com.jdagnogo.welovemarathon.common.ui.component.bottombar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.jdagnogo.welovemarathon.common.ui.component.HomeSections
import com.jdagnogo.welovemarathon.common.ui.theme.BottomMenuBackground
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun BottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
) {
    val currentSection = tabs.first { it.route == currentRoute }
    Surface(
        color = BottomMenuBackground,
        shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
        contentColor = MaterialTheme.colors.primaryVariant,
        modifier = Modifier
            .background(Color.Transparent)
            .navigationBarsPadding(start = false, end = false)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            tabs.forEach { section ->
                val isSelected = section == currentSection
                BottomNavItem(section = section, isSelected = isSelected,
                    onItemClicked = { navigateToRoute(section.route) })
            }
        }
    }
}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomBarPreview() {
    WeLoveMarathonTheme {
        Surface {
            BottomBar(
                tabs = arrayOf(
                    HomeSections.HOME,
                    HomeSections.FAVORITES,
                    HomeSections.ABOUT
                ),
                currentRoute = HomeSections.ABOUT.route,
                navigateToRoute = {}
            )
        }
    }
}
