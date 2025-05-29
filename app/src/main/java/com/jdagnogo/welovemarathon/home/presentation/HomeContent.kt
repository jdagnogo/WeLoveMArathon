package com.jdagnogo.welovemarathon.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.banner.GifBannerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WLMTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.White

val Main_Blue = Color(0xFF1E4F7B)

val CustomColors = lightColors(
    primary = Main_Blue,
    onPrimary = White
)

@Composable
fun WeLoveMarathonTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = CustomColors,
        content = content
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun HomeContent(
    state: HomeState,
    onActivitySelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopBar()
            ActivitiesGridComponent(
                activities = state.activities,
                onActivitySelected = onActivitySelected,
            )
        }
        if (state.banner != null) {
            GifBannerComponent(
                gifBanner = state.banner,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Image(
                painter = rememberImagePainter(
                    data = R.drawable.ic_wlm_logo,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = "Logo",
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = "WE LOVE MARATHON",
                style = WLMTitleStyle.copy(
                    fontSize = 19.sp
                ),
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview(name = "Loading")
@Preview("Dark : Loading", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingComponentPreview() {
    val reducer = HomeReducer()
    val state = reducer.reduce(HomeState(), HomePartialState.LoadingBeaches)
    MaterialTheme {
        HomeContent(
            state = state,
            modifier = Modifier,
            onActivitySelected = {},
        )
    }
}

