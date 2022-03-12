package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.ActivityTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.home.domain.Activities

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ActivitiesGridComponent(
    activities: List<Activities>,
    onActivitySelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
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
            items(activities.size) { index ->
                val activity = activities[index]
                ActivityItem(
                    activities = activity,
                    onActivityClicked = onActivitySelected,
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ActivityItem(
    activities: Activities,
    onActivityClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = MaterialTheme.spacing.small,
        shape = MaterialTheme.shapes.large,
        onClick = { onActivityClicked(activities.ordinal) },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(color = activities.backgroundColor)
                .padding( horizontal = MaterialTheme.spacing.medium)
                .padding(
                    bottom = MaterialTheme.spacing.medium,
                    top = MaterialTheme.spacing.huge,
                )
        ) {
            Image(
                painter = rememberImagePainter(
                    data = activities.icon,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                colorFilter= ColorFilter.tint(Color.White),
                contentDescription = activities.title,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = activities.title,
                style = ActivityTitleStyle,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small)
            )
            Text(
                text = activities.subtitle,
                style = ActivitySubTitleStyle,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.extraSmall)
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun ActivitiesGridComponentPreview() {
    val activities = Activities.values()
    MaterialTheme {
        ActivitiesGridComponent(activities = activities.toList(), {})
    }
}
