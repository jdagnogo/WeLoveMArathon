package com.jdagnogo.welovemarathon.common.ui.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.SpringSpec
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsPadding
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun BottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    color: Color = MaterialTheme.colors.secondary,
    contentColor: Color = MaterialTheme.colors.primaryVariant,
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }
    Surface(
        color = color,
        contentColor = contentColor
    ) {
        DividerComponent(color = Primary)
        val springSpec = SpringSpec<Float>(
            stiffness = 800f,
            dampingRatio = 0.8f
        )
        BottomNavLayout(
            selectedIndex = currentSection.ordinal,
            itemCount = routes.size,
            indicator = { BottomNavIndicator() },
            animSpec = springSpec,
            modifier = Modifier.navigationBarsPadding(start = false, end = false)
        ) {
            tabs.forEach { section ->
                val selected = section == currentSection
                val tint by animateColorAsState(
                    if (selected) {
                        WeLoveMarathonTheme.colors.bottomBarIconSelected
                    } else {
                        MaterialTheme.colors.secondaryVariant
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = section.icon),
                            tint = tint,
                            contentDescription = null
                        )
                    },
                    selected = selected,
                    onSelected = { navigateToRoute(section.route) },
                    animSpec = springSpec,
                    modifier = BottomNavigationItemPadding
                        .clip(BottomNavIndicatorShape)
                )
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
                tabs = arrayOf(HomeSections.HOME,
                    HomeSections.FOOD,
                    HomeSections.SERVICES,
                    HomeSections.FAVORITES),
                currentRoute = HomeSections.FAVORITES.route,
                navigateToRoute = {}
            )
        }
    }
}
