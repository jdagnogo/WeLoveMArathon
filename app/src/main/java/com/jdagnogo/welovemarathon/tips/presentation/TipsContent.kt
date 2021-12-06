package com.jdagnogo.welovemarathon.tips.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.tips.domain.Tips
import com.jdagnogo.welovemarathon.tips.domain.toFakeList

@Composable
fun TipsContent(state: TipsState, modifier: Modifier = Modifier) {
    Surface(modifier = modifier
        .background(WeLoveMarathonTheme.colors.contentBackground)
        .fillMaxSize()
        .statusBarsPadding()
        ) {
        LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxHeight()) {
            item {
                Text(text = "Our Tips", style = MaterialTheme.typography.h2)
            }

            itemsIndexed(state.tips) { index, tips ->
                TipsComponent(tips = tips, index = index)
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(name = "TipsContent")
@Preview("Dark : TipsContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TipsContentPreview() {
    MaterialTheme {
        TipsContent(state = TipsState(tips = Tips().toFakeList()))
    }
}
