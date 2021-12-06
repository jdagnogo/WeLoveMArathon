package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.home.domain.Activities

@ExperimentalFoundationApi
@Composable
fun ActivitiesGridComponent(
    activities: List<Activities>,
    onActivitySelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TitleComponent(title = "Activities", alignRight = true, modifier = Modifier.padding(16.dp))
        LazyRow(modifier = Modifier
            .animateContentSize()) {
            items(activities.size) { index ->
                val activity = activities[index]
                val randomColor: Color = remember { activity.backgroundColor }
                ActivityItem(
                    activities = activity,
                    color = randomColor,
                    onActivityClicked = onActivitySelected,
                )
            }
        }
    }
}

@Composable
fun ActivityItem(
    activities: Activities,
    onActivityClicked: (Int) -> Unit,
    color: Color = Secondary,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .width(120.dp)
            .clickable { onActivityClicked(activities.ordinal) }
    ) {
        ConstraintLayout(modifier = Modifier.background(color = color)) {
            val (iconRef, titleRef) = createRefs()

            Text(text = activities.title,
                color = Color.Black,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(titleRef) {
                        top.linkTo(iconRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

            Image(
                painter = rememberImagePainter(
                    data = activities.icon,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = activities.title,
                modifier = Modifier
                    .size(48.dp)
                    .constrainAs(iconRef) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, 16.dp)
                    })
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ActivitiesGridComponentPreview() {
    val activities = Activities.values()
    MaterialTheme {
        ActivitiesGridComponent(activities = activities.toList(), {})
    }
}
