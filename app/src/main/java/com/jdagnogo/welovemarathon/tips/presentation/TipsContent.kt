package com.jdagnogo.welovemarathon.tips.presentation

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivityTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.tips.domain.Tips

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TipsContent(
    state: TipsState,
    onTipsSelected: (Int) -> Unit = {},
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tips",
            style = TitleStyle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.medium)
        )
        Column(modifier = modifier) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium,
                    top = MaterialTheme.spacing.large,
                    bottom = MaterialTheme.spacing.extraHuge
                ),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier.animateContentSize()
            ) {
                items(state.tips.size) { index ->
                    val tips = remember {
                        state.tips[index]
                    }
                    Card(
                        elevation = MaterialTheme.spacing.small,
                        shape = MaterialTheme.shapes.large,
                        onClick = { onTipsSelected(tips.ordinal) },
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier
                                .fillMaxSize()
                                .background(color = tips.color.toColor)
                                .padding(horizontal = MaterialTheme.spacing.medium)
                                .padding(vertical = MaterialTheme.spacing.medium)
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = tips.icon,
                                    builder = {
                                        crossfade(true)
                                        error(R.drawable.ic_wlm_logo)
                                    }
                                ),
                                colorFilter = ColorFilter.tint(Color.White),
                                contentDescription = tips.title,
                                modifier = Modifier.size(70.dp)
                            )
                            Text(
                                text = tips.title,
                                style = ActivityTitleStyle,
                                modifier = Modifier
                                    .padding(top = MaterialTheme.spacing.small)
                            )
                        }
                    }
                }
            }
        }
    }
    if (state.shouldOpenDialog) {
        TipsDialogComponent(
            item = state.currentSelected,
            onDismissRequest = onDismissRequest,
            modifier = Modifier
        )
    }
}

val String.toColor
    get() = Color(android.graphics.Color.parseColor(this))

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Preview(name = "TipsContent")
@Preview("Dark : TipsContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TipsContentPreview() {
    MaterialTheme {
        TipsContent(
            state = TipsState(
                error = "",
                tips = listOf(
                    Tips("1", "title1"),
                    Tips("2", "title2"),
                )
            ),
            onTipsSelected = {},
            onDismissRequest = {},
            modifier = Modifier,
        )
    }
}
