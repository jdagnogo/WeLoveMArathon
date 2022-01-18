package com.jdagnogo.welovemarathon.tips.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.SecondaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.White
import com.jdagnogo.welovemarathon.tips.domain.Tips
import com.jdagnogo.welovemarathon.tips.domain.toFakeList

@ExperimentalAnimationApi
@Composable
fun TipsContent(state: TipsState, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 4.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxHeight()
        ) {
            item {
                ConstraintLayout(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(400.dp)

                ) {
                    val (titleRef, subTitleRef, imageRef) = createRefs()
                    Image(
                        painter = rememberImagePainter(
                            data = R.drawable.beach,
                            builder = {
                                crossfade(true)
                                error(R.drawable.ic_wlm_logo)
                            }
                        ),
                        contentDescription = "Our Tips",
                        modifier = Modifier
                            .constrainAs(imageRef) {}
                            .padding(horizontal = 4.dp)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(5))
                            .border(2.dp, PrimaryDark, RoundedCornerShape(5))

                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "Our Tips",
                        color = White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.constrainAs(titleRef) {
                            linkTo(
                                start = imageRef.start,
                                end = imageRef.end,
                            )
                            bottom.linkTo(subTitleRef.top)
                        }
                    )

                    Text(
                        text = "Step by step tips to make sure you have the time of your life",
                        color = White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .constrainAs(subTitleRef) {
                                linkTo(
                                    start = imageRef.start,
                                    end = imageRef.end,
                                )
                                bottom.linkTo(imageRef.bottom)
                            }
                            .padding(16.dp)
                    )
                }
            }

            itemsIndexed(state.tips) { index, tips ->
                TipsComponent(tips = tips, index = index)
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview(name = "TipsContent")
@Preview("Dark : TipsContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TipsContentPreview() {
    MaterialTheme {
        TipsContent(state = TipsState(tips = Tips().toFakeList()))
    }
}
