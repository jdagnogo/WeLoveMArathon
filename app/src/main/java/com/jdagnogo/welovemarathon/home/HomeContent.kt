package com.jdagnogo.welovemarathon.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R

@ExperimentalAnimationApi
@Composable
fun HomeContent(state: HomeViewState, modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .height(56.dp)
                    .width(56.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
            Text(text = "We Love Marathon ! ",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun LoadingComponentPreview() {
    val fakeData = listOf(Blog("title1", "title1"), Blog("title2", "title2"))
    MaterialTheme {
        HomeContent(state = HomeViewState.OnBlogSuccess(fakeData), modifier = Modifier)
    }
}
