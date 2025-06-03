package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.ErrorComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun HomeTopBarContent(hasError: Boolean = false, modifier: Modifier = Modifier) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            backgroundColor = Secondary,
            contentColor = Color(0xde000000),
            elevation = 0.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_wlm_logo),
                contentDescription = "Logo",
                colorFilter = ColorFilter.tint(Color(0xFFA3C585)),
                modifier = Modifier
                    .size(50.dp)
            )
        }
        DividerComponent(color = Primary)
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun RunTopBarContentPreview() {
    WeLoveMarathonTheme {
        HomeTopBarContent(modifier = Modifier)
    }
}