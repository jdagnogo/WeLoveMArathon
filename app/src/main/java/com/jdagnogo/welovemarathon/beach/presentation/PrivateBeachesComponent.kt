package com.jdagnogo.welovemarathon.beach.presentation

import android.content.res.Configuration
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach
import com.jdagnogo.welovemarathon.beach.domain.toFakeList

@Composable
fun PrivateBeachesComponent(privateBeaches: List<PrivateBeach>, modifier: Modifier = Modifier) {
    Card() {
        
    }
}

@ExperimentalPagerApi
@Preview(name = "FoodContent")
@Preview("Dark : FoodContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrivateBeachesComponentPreview() {
    MaterialTheme {
        PrivateBeachesComponent(PrivateBeach().toFakeList())
    }
}
