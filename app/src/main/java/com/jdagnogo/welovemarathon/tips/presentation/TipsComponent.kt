package com.jdagnogo.welovemarathon.tips.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.tips.domain.Tips
import com.jdagnogo.welovemarathon.tips.domain.toFakeList

@Composable
fun TipsComponent(tips: Tips, index: Int = 0, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center) {
        val isOdd = remember { index % 2 != 0 }
        Text(
            text = tips.title,
            color = Primary,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        Text(
            text = tips.description,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        )

        Image(
            painter = rememberImagePainter(
                data = tips.image,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = tips.title,
            modifier = Modifier
                .padding(top = 16.dp)
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(text = tips.imageDescription,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 24.dp, end = 24.dp))

        Image(
            painter = rememberImagePainter(
                data = R.drawable.separator,
            ),
            colorFilter = ColorFilter.tint(PrimaryDark),
            contentDescription = tips.title,
            modifier = Modifier
                .height(150.dp.takeIf { isOdd } ?: 100.dp)
                .align(CenterHorizontally)
                .padding(
                    top = 0.dp.takeIf { isOdd } ?: 16.dp,
                    start = 16.dp.takeIf { isOdd } ?: 0.dp,
                    end = 16.dp.takeIf { isOdd } ?: 0.dp),
            contentScale = ContentScale.Inside.takeIf { isOdd } ?: ContentScale.Crop
        )
    }
}

@ExperimentalPagerApi
@Preview(name = "TipsComponent")
@Preview("Dark : TipsComponent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TipsComponentPreview() {
    MaterialTheme {
        TipsComponent(tips = Tips().toFakeList().first())
    }
}