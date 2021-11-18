package com.jdagnogo.welovemarathon.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
fun HomeContent(state: HomeViewState.OnBlogSuccess, modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_wlm_logo),
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
            Box(modifier = Modifier
                .height(50.dp)
                .padding(top = 16.dp, end = 16.dp)
                .align(Alignment.End)) {
                // TODO : Background image
                Text(text = "Blogs")
            }

            Spacer(Modifier.height(8.dp))

            BlogList(blogs = state.data, modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth())
        }
    }
}

@Composable
fun BlogList(blogs: List<Blog>, modifier: Modifier) {
    LazyColumn(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)) {
        itemsIndexed(blogs) { _, blog ->
            BlogItem(blog = blog)
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun LoadingComponentPreview() {
    val fakeData = Blog().fakeList()

    MaterialTheme {
        HomeContent(state = HomeViewState.OnBlogSuccess(fakeData), modifier = Modifier)
    }
}
