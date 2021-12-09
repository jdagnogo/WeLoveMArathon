package com.jdagnogo.welovemarathon.run.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.blog.domain.Blog
import com.jdagnogo.welovemarathon.blog.domain.fakeList
import com.jdagnogo.welovemarathon.blog.presentation.blogList
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.run.domain.Run
import com.jdagnogo.welovemarathon.run.domain.fakeList

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun RunContent(state: RunState, modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .background(WeLoveMarathonTheme.colors.contentBackground)
            .animateContentSize()) {
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()) {
                    TitleComponent(
                        title = "Marathon Run",
                        alignRight = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.End))

                    RunComponentList(state.runs,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize())

                    TitleComponent(
                        title = "Blogs",
                        alignRight = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.End))
                }
            }
            blogList(blogs = state.blogs, scope = this)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(name = "Loading")
@Preview("Dark : Loading", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingComponentPreview() {
    val reducer = RunReducer()
    val state = reducer.reduce(RunState(), RunPartialState.Loading)
    MaterialTheme {
        RunContent(state = state, modifier = Modifier)
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(name = "Blogs")
@Preview("Dark : Blogs", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BlogsComponentPreview() {
    val reducer = RunReducer()
    val state = reducer.reduce(RunState(), RunPartialState.OnBlogsSuccess(Blog().fakeList()))
    MaterialTheme {
        RunContent(state = state, modifier = Modifier)
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(name = "Runs")
@Preview("Dark : Runs", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RunsComponentPreview() {
    val reducer = RunReducer()
    val state =
        reducer.reduce(RunState(), RunPartialState.OnRunsSuccess(Run().fakeList()))
    MaterialTheme {
        RunContent(state = state, modifier = Modifier)
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(name = "Full content")
@Composable
fun RunComponentPreview() {
    val reducer = RunReducer()
    val state =
        reducer.reduce(RunState(), RunPartialState.OnRunsSuccess(Run().fakeList()))
    val finalState = reducer.reduce(state, RunPartialState.OnBlogsSuccess(Blog().fakeList()))
    MaterialTheme {
        RunContent(state = finalState, modifier = Modifier)
    }
}
