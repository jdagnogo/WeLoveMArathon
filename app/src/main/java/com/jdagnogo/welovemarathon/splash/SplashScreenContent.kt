package com.jdagnogo.welovemarathon.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun SplashScreenContent(scale: Float, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_wlm_logo),
            contentDescription = "Logo",
            tint = Color.White,
            modifier = Modifier
                .size(150.dp)
                .scale(scale)
        )

        Text(
            text = "We Love Marathon",
            modifier = Modifier.padding(top = MaterialTheme.spacing.small)
        )
    }
}
