package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun HomeTopBarContent(modifier: Modifier) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            backgroundColor = Secondary,
            contentColor = Color(0xde000000),
            elevation = 0.dp
        ) {
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_wlm_logo),
                    contentDescription = "",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                )
                Text(text = "We Love Marathon ! ",
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 16.dp)
                )
            }
        }
        DividerComponent(color = Primary)
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun HomeTopBarContentPreview() {

    WeLoveMarathonTheme {
        HomeTopBarContent(modifier = Modifier)
    }
}
